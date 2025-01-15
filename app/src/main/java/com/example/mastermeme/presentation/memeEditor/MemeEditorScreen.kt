package com.example.mastermeme.presentation.memeEditor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import coil3.compose.AsyncImage
import com.example.mastermeme.R
import com.example.mastermeme.presentation.components.MasterMemeScaffold
import com.example.mastermeme.presentation.components.MasterMemeToolBar
import com.example.mastermeme.ui.theme.MasterMemeBlack
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MemeEditorScreenRoot(
    memeUri: String,
    onBackClick: () -> Unit,
) {
    MemeEditorScreen(
        memeUri = memeUri,
        onBackClick = onBackClick
    )
}

@Composable
private fun MemeEditorScreen(
    memeUri: String,
    onBackClick: () -> Unit,
) {
    MasterMemeScaffold(
        topAppBar = {
            MasterMemeToolBar(
                toolBarTitle = stringResource(R.string.new_meme),
                showBackButton = true,
                onBackClick = {
                    onBackClick()
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
    }
}

@PreviewScreenSizes
@Composable
private fun MemeEditorScreenPreview() = MasterMemeTheme {
    MemeEditorScreen(
        memeUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
        onBackClick = {}
    )
}