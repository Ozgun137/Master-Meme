package com.example.mastermeme.presentation.memeEditor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.mastermeme.R
import com.example.mastermeme.presentation.components.MasterMemeDialog
import com.example.mastermeme.presentation.components.MasterMemeScaffold
import com.example.mastermeme.presentation.components.MasterMemeSlider
import com.example.mastermeme.presentation.components.MasterMemeToolBar
import com.example.mastermeme.presentation.components.MemeEditorBottomBar
import com.example.mastermeme.presentation.memeEditor.components.DraggableText
import com.example.mastermeme.ui.theme.MasterMemeGradientFirst
import com.example.mastermeme.ui.theme.MasterMemeGradientSecond
import com.example.mastermeme.ui.theme.MasterMemePrimaryFixed
import com.example.mastermeme.ui.theme.MasterMemeSecondary
import com.example.mastermeme.ui.theme.MasterMemeTheme
import com.example.mastermeme.ui.theme.MasterMemeWhite
import com.example.mastermeme.ui.theme.Purple80
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemeEditorScreenRoot(
    memeUri: String,
    onLeaveEditor: () -> Unit,
    viewModel: MemeEditorViewModel = koinViewModel()
) {

    val memeEditorUiState by viewModel.memeEditorUiState.collectAsStateWithLifecycle()

    MemeEditorScreen(
        memeUri = memeUri,
        onAction = { action ->
            when (action) {
                MemeEditorAction.OnLeaveEditorClicked -> onLeaveEditor()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        memeEditorUiState = memeEditorUiState
    )
}

@Composable
private fun MemeEditorScreen(
    memeEditorUiState: MemeEditorUiState,
    memeUri: String,
    onAction: (MemeEditorAction) -> Unit,
) {

    var imageWidth by remember { mutableFloatStateOf(0f) }
    var imageHeight by remember { mutableFloatStateOf(0f) }

    MasterMemeScaffold(
        topAppBar = {
            MasterMemeToolBar(
                toolBarTitle = stringResource(R.string.new_meme),
                showBackButton = true,
                onBackClick = {
                    onAction(MemeEditorAction.OnBackClicked)
                }
            )
        },

        bottomAppBar = {
            MemeEditorBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MasterMemeGradientFirst
                            .copy(alpha = 0.05f),
                        shape = RectangleShape
                    )
                    .background(MaterialTheme.colorScheme.surfaceContainer)

            ) {
                if (memeEditorUiState.isTextSelected) {
                    BottomBarViewTextSelected()
                } else {
                    DefaultBottomBarView(onAction = onAction)
                }

            }

        }
    ) { padding ->
        val screenHeight = LocalDensity.current.run {
            LocalConfiguration.current.screenHeightDp.toDp()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(top = screenHeight / 3)
        ) {

            AsyncImage(
                model = memeUri,
                contentDescription = stringResource(R.string.meme_content_description),
                modifier = Modifier
                    .aspectRatio(1f)
                    .onGloballyPositioned {
                        imageWidth = it.size.width.toFloat()
                        imageHeight = it.size.height.toFloat()
                    },
                contentScale = ContentScale.Crop
            )

            if (memeEditorUiState.textBoxList.isNotEmpty()) {
                memeEditorUiState.textBoxList.forEach { textBoxUI ->
                    DraggableText(
                        id = textBoxUI.id,
                        textBoxUI = textBoxUI,
                        imageWidth = imageWidth,
                        imageHeight = imageHeight,
                        onTextPositionChanged = { offset ->
                            onAction(
                                MemeEditorAction.OnTextPositionChanged(
                                    id = textBoxUI.id,
                                    offset = offset
                                )
                            )
                        },
                        onTextSelected = { selectedTextID ->
                            onAction(
                                MemeEditorAction.OnTextSelected(
                                    id = selectedTextID
                                )
                            )
                        }
                    )
                }
            }
        }

        if (memeEditorUiState.shouldShowLeaveEditorDialog) {
            MasterMemeDialog(
                title = stringResource(R.string.leave_editor),
                memeEditorDialogContent = {
                    Text(
                        text = stringResource(R.string.leave_editor_description),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = FontFamily(
                                Font(R.font.manrope_regular)
                            ),
                            fontSize = 14.sp,
                            lineHeight = 21.86.sp,
                            fontWeight = FontWeight.Normal
                        ),
                    )
                },
                primaryButton = {
                    TextButton(
                        onClick = {
                            onAction(MemeEditorAction.OnLeaveEditorClicked)
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.leave),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.secondary,
                                fontFamily = FontFamily(
                                    Font(R.font.manrope_regular)
                                ),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                },

                secondaryButton = {
                    TextButton(
                        onClick = {
                            onAction(MemeEditorAction.OnLeaveEditorDialogDismissed)
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.secondary,
                                fontFamily = FontFamily(
                                    Font(R.font.manrope_regular)
                                ),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                },

                onDismiss = { onAction(MemeEditorAction.OnLeaveEditorDialogDismissed) }
            )
        }
    }
}

@Composable
private fun AddTextButton(
    onAction: (MemeEditorAction) -> Unit
) {
    OutlinedButton(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp,
            Brush.linearGradient(
                colors = listOf(
                    MasterMemeGradientFirst,
                    Purple80
                )
            )
        ),
        onClick = {
            onAction(MemeEditorAction.OnAddTextClicked)
        },
        content = {
            Text(
                text = stringResource(R.string.add_text),
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.manrope_regular)
                    ),
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MasterMemeGradientFirst,
                            Purple80
                        )
                    ),
                    lineHeight = 20.sp,
                    fontSize = 16.sp
                )
            )
        }
    )
}

@Composable
private fun SaveMemeButton() {
    Button(
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent // Set to transparent to rely on the gradient brush
        ),
        onClick = {}
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MasterMemeGradientFirst,
                            MasterMemeGradientSecond
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                ),
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                text = stringResource(R.string.save_meme),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(
                        Font(R.font.manrope_regular)
                    ),
                    color = MasterMemePrimaryFixed,
                    lineHeight = 20.sp,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
private fun DefaultBottomBarView(onAction: (MemeEditorAction) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        AddTextButton(onAction = onAction)
        SaveMemeButton()
    }
}

@Composable
private fun BottomBarViewTextSelected() {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_close_24),
                contentDescription = "adas",
                tint = MasterMemeWhite
            )
        }


        Text(
            text = "Aa",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp,
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.manrope_regular
                    )
                )

            )
        )


        MasterMemeSlider(
            modifier = Modifier.weight(1f),
            color = MasterMemeSecondary,
            trackStrokeWidth = 1.dp,
            trackHeight = 1.dp,
            trackCornerRadius = 1.dp
        )


        Text(
            text = "Aa",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp,
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.manrope_regular
                    )
                )

            )
        )

        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_check_24),
                contentDescription = "adas",
                tint = MasterMemeWhite
            )
        }


    }
}


@Preview
@Composable
private fun MemeEditorScreenPreview() = MasterMemeTheme {
    MemeEditorScreen(
        memeUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
        onAction = {},
        memeEditorUiState = MemeEditorUiState(),
    )
}