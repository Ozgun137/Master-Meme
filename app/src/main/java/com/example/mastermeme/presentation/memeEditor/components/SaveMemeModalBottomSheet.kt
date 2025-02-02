package com.example.mastermeme.presentation.memeEditor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mastermeme.R
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun SaveMemeModalBottomSheet(
    modifier: Modifier = Modifier,
    onSaveToDeviceClicked: () -> Unit,
    onShareMemeClicked: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        SaveMemeModalBottomSheetItem(
            iconId = R.drawable.download,
            title = stringResource(R.string.save_to_device),
            description = stringResource(R.string.save_to_files),
            contentDescription = stringResource(R.string.save_to_device_icon),
            onItemClick = onSaveToDeviceClicked
        )

        SaveMemeModalBottomSheetItem(
            iconId = R.drawable.share,
            title = stringResource(R.string.share_the_meme),
            description = stringResource(R.string.share_meme_description),
            contentDescription = stringResource(R.string.share_meme_icon),
            onItemClick = onShareMemeClicked
        )

    }

}

@Preview
@Composable
private fun SaveMemeModalBottomSheetPreview() = MasterMemeTheme {
    SaveMemeModalBottomSheet(
        onSaveToDeviceClicked = {},
        onShareMemeClicked = { }
    )
}