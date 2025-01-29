package com.example.mastermeme.presentation.memeEditor.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.mastermeme.R
import com.example.mastermeme.ui.theme.MasterMemeBlack
import com.example.mastermeme.ui.theme.MasterMemeWhite

@Composable
fun StrokeTextView(
    text: String,
    textSize : Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.wrapContentSize()) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FontFamily(Font(resId = R.font.impact)),
                color = MasterMemeBlack,
                fontSize = textSize.sp,
                drawStyle = Stroke(
                    width = 8f
                )
            )
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FontFamily(Font(resId = R.font.impact)),
                color = MasterMemeWhite,
                fontSize = textSize.sp,
            )
        )
    }
}