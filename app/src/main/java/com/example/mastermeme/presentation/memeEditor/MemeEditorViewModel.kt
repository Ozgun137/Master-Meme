package com.example.mastermeme.presentation.memeEditor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MemeEditorViewModel : ViewModel() {

    private val _memeEditorUiState = MutableStateFlow(MemeEditorUiState())
    val memeEditorUiState = _memeEditorUiState.asStateFlow()


    fun onAction(action: MemeEditorAction) {
        when (action) {
            MemeEditorAction.OnBackClicked -> {
                _memeEditorUiState.update {
                    it.copy(shouldShowLeaveEditorDialog = true)
                }
            }

            MemeEditorAction.OnLeaveEditorDialogDismissed -> {
                _memeEditorUiState.update {
                    it.copy(shouldShowLeaveEditorDialog = false)
                }
            }

            else -> {}
        }

    }
}