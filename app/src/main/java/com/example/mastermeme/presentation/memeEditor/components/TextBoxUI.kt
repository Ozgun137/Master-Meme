package com.example.mastermeme.presentation.memeEditor.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class TextBoxUI(
    val id : Int,
    val text: String,
    val textPosition : Offset,
    val fontSize: TextUnit = 40.sp,
    val textColor : Color,
)
