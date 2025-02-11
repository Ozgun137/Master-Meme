package com.example.mastermeme.presentation.memeEditor

data class MemeEditorUiState(
    val isMemeSaved: Boolean = false,
    val selectedText: TextBoxUI? = null,
    val shouldShowLeaveEditorDialog:Boolean = false,
    val shouldShowUpdateTextDialog: Boolean = false,
    val shouldShowSaveMemeSheet : Boolean = false,
    val textBoxList : List<TextBoxUI> = emptyList(),
    val uiStatesHistory : Map<Int, ArrayDeque<TextBoxUI>> = mapOf(),
    val editingState: TextBoxUI? = null
)
