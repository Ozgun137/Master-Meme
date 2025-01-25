package com.example.mastermeme.presentation.memeEditor

import com.example.mastermeme.presentation.memeEditor.components.TextBoxUI

data class MemeEditorUiState(
    val shouldShowLeaveEditorDialog:Boolean = false,
    val textBoxList : List<TextBoxUI> = emptyList()
)
