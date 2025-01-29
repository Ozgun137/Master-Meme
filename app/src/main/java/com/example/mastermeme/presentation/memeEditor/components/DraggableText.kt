package com.example.mastermeme.presentation.memeEditor.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.mastermeme.R
import com.example.mastermeme.ui.theme.MasterMemeWhite

@Composable
fun DraggableText(
    id: Int,
    imageHeight: Float,
    imageWidth: Float,
    isSelected: Boolean = false,
    onTextDeleted: (Int) -> Unit,
    onTextPositionChanged: (Offset) -> Unit,
    onTextSelected: (Int) -> Unit,
    textBoxUI: TextBoxUI,
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
            .border(
                shape = RoundedCornerShape(4.dp),
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) MasterMemeWhite else Color.Transparent,
            )
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
            textSize = textBoxUI.fontSize,
            modifier = Modifier
                .onGloballyPositioned {
                    textSize = it.size
                }
                .padding(8.dp)
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .offset(x = (8).dp, y = (-12).dp),
                    onClick = { onTextDeleted(textBoxUI.id)}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.red_cancel_svg),
                        contentDescription = stringResource(R.string.deleteText),
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }

}

