package com.example.mastermeme.presentation.memeEditor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.mastermeme.R
import com.example.mastermeme.presentation.components.MasterMemeDialog
import com.example.mastermeme.presentation.components.MasterMemeScaffold
import com.example.mastermeme.presentation.components.MasterMemeToolBar
import com.example.mastermeme.ui.theme.MasterMemeBlack
import com.example.mastermeme.ui.theme.MasterMemeTheme
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
            when(action) {
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

    MasterMemeScaffold(
        topAppBar = {
            MasterMemeToolBar(
                toolBarTitle = stringResource(R.string.new_meme),
                showBackButton = true,
                onBackClick = {
                   onAction(MemeEditorAction.OnBackClicked)
                }
            )
        }
    ) { padding ->
        val screenHeight = LocalDensity.current.run {
            LocalConfiguration.current.screenHeightDp.toDp()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MasterMemeBlack)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AsyncImage(
                model = memeUri,
                contentDescription = stringResource(R.string.meme_content_description),
                modifier = Modifier
                    .padding(top = screenHeight / 3)
                    .aspectRatio(1f),
                contentScale = ContentScale.FillBounds
            )

        }

        if(memeEditorUiState.shouldShowLeaveEditorDialog) {
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

                onDismiss = { onAction(MemeEditorAction.OnLeaveEditorDialogDismissed)}
            )
        }
    }
}

@PreviewScreenSizes
@Composable
private fun MemeEditorScreenPreview() = MasterMemeTheme {
    MemeEditorScreen(
        memeUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
        onAction = {},
        memeEditorUiState = MemeEditorUiState(),
    )
}