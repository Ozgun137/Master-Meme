package com.example.mastermeme.presentation.memeList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.mastermeme.R
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MemeGridItem(
    modifier: Modifier = Modifier,
    model: Any? = null,
    contentDescription: String = ""
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .wrapContentSize()
            .aspectRatio(1f)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(model = model),
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun MemeCardItemPreview() = MasterMemeTheme {
    MemeGridItem(
        model = R.drawable.vt4i_27
    )
}
