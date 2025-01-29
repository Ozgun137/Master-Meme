@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mastermeme.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mastermeme.ui.theme.MasterMemeSecondary
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MasterMemeSlider(
    modifier: Modifier = Modifier,
    textSize : Float,
    trackHeight : Dp,
    trackCornerRadius : Dp,
    trackStrokeWidth: Dp,
    color : Color = MasterMemeSecondary,
    onValueChanged : (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
) {

    var sliderPosition by rememberSaveable {
        mutableFloatStateOf(textSize)
    }

    LaunchedEffect(textSize) {
        sliderPosition = textSize
    }

    Slider(
        modifier = modifier,
        value = sliderPosition,
        valueRange = valueRange,
        onValueChange = {
            sliderPosition = it
            onValueChanged(it)
        },
        thumb = {
            Canvas(
                modifier = Modifier.size(24.dp)
            ) {
                drawCircle(
                    color = color.copy(alpha = 0.3f),
                    radius = size.width / 2
                )

                drawCircle(
                    color = color,
                    radius = size.width / 3
                )
            }
        },

        track = { _ ->
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
            ) {
                drawRoundRect(
                    color = color,
                    size = size,
                    cornerRadius = CornerRadius(trackCornerRadius.toPx()),
                    style = Stroke(width = trackStrokeWidth.toPx())
                )
            }
        }

    )

}


@Preview
@Composable
private fun MasterMemeSliderPreview()  = MasterMemeTheme {
    MasterMemeSlider(
        trackHeight = 1.dp,
        trackCornerRadius = 1.dp,
        trackStrokeWidth = 1.dp,
        onValueChanged = {},
        textSize = 42f,
        valueRange = 12f..72f
    )
}

