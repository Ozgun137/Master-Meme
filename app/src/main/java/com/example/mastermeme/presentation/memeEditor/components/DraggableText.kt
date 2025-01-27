package com.example.mastermeme.presentation.memeEditor.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun DraggableText(
    id: Int,
    textBoxUI: TextBoxUI,
    onTextPositionChanged: (Offset) -> Unit,
    onTextSelected : (Int) -> Unit,
    imageWidth : Float,
    imageHeight: Float
) {

    var offset by remember { mutableStateOf(textBoxUI.textPosition) }
    var textSize by remember { mutableStateOf(IntSize(0, 0)) }


    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offset.x.toInt(),
                    offset.y.toInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {},
                    onDragEnd = {},
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val maxX = imageWidth - textSize.width
                        val maxY = imageHeight - textSize.height

                        val newOffsetX = (offset.x + dragAmount.x)
                            .coerceIn(0f, maxX)
                        val newOffsetY = (offset.y + dragAmount.y)
                            .coerceIn(0f, maxY)

                        offset = Offset(newOffsetX, newOffsetY)
                        onTextPositionChanged(offset)
                    },
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onTextSelected(id)
                    }
                )
            }
    ) {
        StrokeTextView(
            text = textBoxUI.text,
            modifier = Modifier
                .onGloballyPositioned {
                    textSize = it.size
                }
                .padding(8.dp)
        )

    }

}