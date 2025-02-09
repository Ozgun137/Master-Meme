@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.mastermeme.presentation.memeEditor

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.example.mastermeme.presentation.components.MasterMemeModalBottomSheet
import com.example.mastermeme.presentation.memeEditor.components.DraggableText
import com.example.mastermeme.presentation.memeEditor.components.SaveMemeModalBottomSheet
import com.example.mastermeme.presentation.util.ObserveAsEvents
import com.example.mastermeme.ui.theme.MasterMemeGradientFirst
import com.example.mastermeme.ui.theme.MasterMemeGradientSecond
import com.example.mastermeme.ui.theme.MasterMemePrimaryFixed
import com.example.mastermeme.ui.theme.MasterMemePrimaryFixedVariant
import com.example.mastermeme.ui.theme.MasterMemeSecondary
import com.example.mastermeme.ui.theme.MasterMemeTheme
import com.example.mastermeme.ui.theme.MasterMemeWhite
import com.example.mastermeme.ui.theme.Purple80
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemeEditorScreenRoot(
    memeUri: String, onLeaveEditor: () -> Unit, viewModel: MemeEditorViewModel = koinViewModel()
) {

    val memeEditorUiState by viewModel.memeEditorUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) {
        when (it) {
            MemeEditorEvent.MemeSaved -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.meme_successfully_saved),
                    Toast.LENGTH_LONG
                ).show()
            }

            MemeEditorEvent.MemeSaveFailed -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.meme_can_not_be_saved),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    MemeEditorScreen(
        memeUri = memeUri, onAction = { action ->
            when (action) {
                MemeEditorAction.OnLeaveEditorClicked -> onLeaveEditor()
                else -> Unit
            }
            viewModel.onAction(action)
        }, memeEditorUiState = memeEditorUiState
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

    val captureController = rememberCaptureController()

    MasterMemeScaffold(topAppBar = {
        MasterMemeToolBar(toolBarTitle = stringResource(R.string.new_meme),
            showBackButton = true,
            onBackClick = {
                onAction(MemeEditorAction.OnBackClicked)
            })
    },

        bottomAppBar = {
            MemeEditorBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MasterMemeGradientFirst.copy(alpha = 0.05f),
                        shape = RectangleShape
                    )
                    .background(MaterialTheme.colorScheme.surfaceContainer)

            ) {
                if (memeEditorUiState.editingState == null) {
                    DefaultBottomBarView(onAction = onAction)
                } else {
                    BottomBarViewTextSelected(
                        onApplyChangesClicked = { onAction(MemeEditorAction.OnApplyChangesClicked) },
                        onCancelChangesClicked = { onAction(MemeEditorAction.OnCancelChangesClicked) },
                        onSliderValueChanged = {
                            onAction(MemeEditorAction.OnSliderValueChanged(it))
                        },
                        selectedText = memeEditorUiState.editingState
                    )
                }

            }

        }) { padding ->
        val screenHeight = LocalDensity.current.run {
            LocalConfiguration.current.screenHeightDp.toDp()
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                onAction(MemeEditorAction.OnRootViewClicked)
            }
            .padding(padding)
            .padding(top = screenHeight / 3)
            .capturable(captureController)
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
                contentScale = ContentScale.FillBounds
            )

            if (memeEditorUiState.textBoxList.isNotEmpty()) {
                memeEditorUiState.textBoxList.forEach { textBoxUI ->
                    key(textBoxUI.id) {
                        val isEditing = memeEditorUiState.editingState?.id == textBoxUI.id
                        val displayedTextBoxUI = if (isEditing) {
                            memeEditorUiState.editingState!!
                        } else {
                            textBoxUI
                        }

                        DraggableText(id = displayedTextBoxUI.id,
                            isSelected = displayedTextBoxUI.id == memeEditorUiState.selectedText?.id,
                            textBoxUI = displayedTextBoxUI,
                            imageWidth = imageWidth,
                            imageHeight = imageHeight,
                            onDoubleTap = { textID ->
                                onAction(MemeEditorAction.OnTextDoubleTapped(textID))
                            },
                            onTextDeleted = { deletedTextID ->
                                onAction(MemeEditorAction.OnTextDeleteClicked(deletedTextID))
                            },
                            onTextPositionChanged = { offset ->
                                onAction(
                                    MemeEditorAction.OnTextPositionChanged(
                                        id = displayedTextBoxUI.id, offset = offset
                                    )
                                )
                            },
                            onTextSelected = { selectedTextID ->
                                onAction(
                                    MemeEditorAction.OnTextSelected(
                                        id = selectedTextID
                                    )
                                )
                            })
                    }
                }
            }
        }

        if (memeEditorUiState.shouldShowLeaveEditorDialog) {
            MasterMemeDialog(title = stringResource(R.string.leave_editor),
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
                    TextButton(onClick = {
                        onAction(MemeEditorAction.OnLeaveEditorDialogDismissed)
                    }) {
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

                onDismiss = { onAction(MemeEditorAction.OnLeaveEditorDialogDismissed) })
        }

        if (memeEditorUiState.shouldShowUpdateTextDialog) {
            MasterMemeDialog(
                onDismiss = { onAction(MemeEditorAction.OnEditTextCancelClicked) },
                primaryButton = {
                    TextButton(onClick = {
                        onAction(MemeEditorAction.OnTextChangeApplied)
                    }) {
                        Text(
                            text = "Ok", style = TextStyle(
                                color = MaterialTheme.colorScheme.secondary,
                                fontFamily = FontFamily(
                                    Font(
                                        resId = R.font.manrope_regular
                                    )
                                ),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },

                secondaryButton = {
                    TextButton(onClick = {
                        onAction(MemeEditorAction.OnEditTextCancelClicked)
                    }) {
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

                title = "Text",

                memeEditorDialogContent = {
                    BasicTextField(value = memeEditorUiState.editingState?.text ?: "",
                        onValueChange = { text ->
                            onAction(
                                MemeEditorAction.OnTextChanged(
                                    memeEditorUiState.editingState?.id ?: -1, text
                                )
                            )
                        },
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(
                                Font(
                                    resId = R.font.manrope_regular
                                )
                            )
                        ),
                        cursorBrush = SolidColor(Color.White),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 0.dp)
                                ) {
                                    innerTextField()
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                HorizontalDivider(
                                    thickness = 2.dp, color = MasterMemePrimaryFixedVariant
                                )
                            }
                        })
                },
            )
        }

        if(memeEditorUiState.shouldShowSaveMemeSheet) {
            MasterMemeModalBottomSheet (
                modalBottomSheetHeightRatio = 0.2f,
                isSheetOpen = true,
                onSheetDismissed = {
                    onAction(MemeEditorAction.OnBottomSheetDismissed)
                },
                modalBottomSheetContent = {
                      SaveMemeModalBottomSheet(
                          onSaveToDeviceClicked = {
                              onAction(MemeEditorAction.OnSaveToDeviceClicked(captureController))
                          },
                          onShareMemeClicked = {}
                      )
                }
            )
        }
    }
}

@Composable
private fun AddTextButton(
    onAddTextClicked: () -> Unit,
) {
    OutlinedButton(shape = RoundedCornerShape(8.dp), border = BorderStroke(
        1.dp, Brush.linearGradient(
            colors = listOf(
                MasterMemeGradientFirst, Purple80
            )
        )
    ), onClick = {
        onAddTextClicked()
    }, content = {
        Text(
            text = stringResource(R.string.add_text), style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.manrope_regular)
                ), fontWeight = FontWeight.Bold, brush = Brush.linearGradient(
                    colors = listOf(
                        MasterMemeGradientFirst, Purple80
                    )
                ), lineHeight = 20.sp, fontSize = 16.sp
            )
        )
    })
}

@Composable
private fun SaveMemeButton(
    onSaveMemeClicked: () -> Unit,
) {
    Button(shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent // Set to transparent to rely on the gradient brush
    ), onClick = { onSaveMemeClicked() }) {
        Box(
            modifier = Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MasterMemeGradientFirst, MasterMemeGradientSecond
                    )
                ), shape = RoundedCornerShape(8.dp)
            ),
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp, vertical = 8.dp
                ), text = stringResource(R.string.save_meme), style = TextStyle(
                    fontWeight = FontWeight.Bold, fontFamily = FontFamily(
                        Font(R.font.manrope_regular)
                    ), color = MasterMemePrimaryFixed, lineHeight = 20.sp, fontSize = 16.sp
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
        AddTextButton(onAddTextClicked = { onAction(MemeEditorAction.OnAddTextClicked) })
        SaveMemeButton(onSaveMemeClicked = { onAction(MemeEditorAction.OnSaveMemeClicked) })
    }
}

@Composable
private fun BottomBarViewTextSelected(
    onApplyChangesClicked: () -> Unit,
    onCancelChangesClicked: () -> Unit,
    onSliderValueChanged: (Float) -> Unit,
    selectedText: TextBoxUI,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { onCancelChangesClicked() }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_close_24),
                contentDescription = stringResource(R.string.cancelChanges),
                tint = MasterMemeWhite
            )
        }


        Text(
            text = "Aa", style = TextStyle(
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
            textSize = selectedText.fontSize,
            trackStrokeWidth = 1.dp,
            trackHeight = 1.dp,
            trackCornerRadius = 1.dp,
            onValueChanged = { sliderValue ->
                onSliderValueChanged(sliderValue)
            },
            valueRange = 12f..72f
        )


        Text(
            text = "Aa", style = TextStyle(
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

        IconButton(onClick = { onApplyChangesClicked() }) {
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