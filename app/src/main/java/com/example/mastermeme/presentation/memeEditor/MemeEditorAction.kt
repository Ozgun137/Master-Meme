package com.example.mastermeme.presentation.memeEditor

import androidx.compose.ui.geometry.Offset

sealed interface MemeEditorAction {
    data class OnTextDoubleTapped(val id: Int) : MemeEditorAction
    data class OnSliderValueChanged(val sliderValue: Float) : MemeEditorAction
    data class OnTextChanged(val id: Int, val text: String) : MemeEditorAction
    data class OnTextDeleteClicked(val id : Int) : MemeEditorAction
    data class OnTextPositionChanged(val id: Int, val offset: Offset) : MemeEditorAction
    data class OnTextSelected(val id: Int) : MemeEditorAction
    data object OnAddTextClicked : MemeEditorAction
    data object OnApplyChangesClicked : MemeEditorAction
    data object OnCancelChangesClicked: MemeEditorAction
    data object OnBackClicked : MemeEditorAction
    data object OnEditTextCancelClicked : MemeEditorAction
    data object OnLeaveEditorClicked : MemeEditorAction
    data object OnLeaveEditorDialogDismissed : MemeEditorAction
    data object OnRootViewClicked : MemeEditorAction
    data object OnTextChangeApplied : MemeEditorAction
}