package com.example.mastermeme.presentation.memeEditor

import com.example.mastermeme.presentation.memeEditor.components.TextBoxUI

data class MemeEditorUiState(
    val isTextSelected: Boolean = false,
    val shouldShowLeaveEditorDialog:Boolean = false,
    val textBoxList : List<TextBoxUI> = emptyList(),
)
