package com.example.mastermeme.presentation.memeEditor

import com.example.mastermeme.presentation.memeEditor.components.TextBoxUI

data class MemeEditorUiState(
    val selectedText: TextBoxUI? = null,
    val shouldShowLeaveEditorDialog:Boolean = false,
    val textBoxList : List<TextBoxUI> = emptyList(),
    val uiStatesHistory : Map<Int, ArrayDeque<TextBoxUI>> = mapOf(),
    val editingState: TextBoxUI? = null
)
