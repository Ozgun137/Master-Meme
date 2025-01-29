package com.example.mastermeme.presentation.memeEditor.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class TextBoxUI(
    val id : Int,
    val text: String,
    val textPosition : Offset,
    val fontSize: Float = 42f,
    val textColor : Color,
    val isSelected : Boolean = false
)
