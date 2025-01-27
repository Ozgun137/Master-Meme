package com.example.mastermeme.presentation.memeEditor

import androidx.compose.ui.geometry.Offset

sealed interface MemeEditorAction {
    data object OnBackClicked : MemeEditorAction
    data object OnLeaveEditorDialogDismissed : MemeEditorAction
    data object OnLeaveEditorClicked : MemeEditorAction
    data object OnAddTextClicked : MemeEditorAction
    data class OnTextPositionChanged(val id: Int, val offset: Offset): MemeEditorAction
    data class OnTextSelected(val id : Int) : MemeEditorAction
}