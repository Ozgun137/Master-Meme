package com.example.mastermeme.presentation.memeEditor

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.mastermeme.MemeEditor
import com.example.mastermeme.presentation.memeEditor.components.TextBoxUI
import com.example.mastermeme.ui.theme.MasterMemeWhite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MemeEditorViewModel : ViewModel() {

    private val _memeEditorUiState = MutableStateFlow(MemeEditorUiState())
    val memeEditorUiState = _memeEditorUiState.asStateFlow()

    private var textBoxId = 0


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

            MemeEditorAction.OnAddTextClicked -> {
                textBoxId++
                val currentTextBoxList = _memeEditorUiState.value.textBoxList.toMutableList()

                currentTextBoxList.add(
                    TextBoxUI(
                        id = textBoxId,
                        text = "TAP TWICE TO EDIT",
                        textPosition = Offset(0f, 0f),
                        textColor = MasterMemeWhite
                    )
                )

                _memeEditorUiState.update {
                    it.copy(
                        textBoxList = currentTextBoxList
                    )
                }

            }

            is MemeEditorAction.OnTextPositionChanged -> {
                _memeEditorUiState.update { currentState ->
                    val updatedList = currentState.textBoxList.map { textBoxUI ->
                        if (textBoxUI.id == action.id) {
                            textBoxUI.copy(textPosition = action.offset)
                        } else {
                            textBoxUI
                        }
                    }
                    currentState.copy(textBoxList = updatedList)
                }
            }

            is MemeEditorAction.OnTextSelected -> {
                _memeEditorUiState.update {
                    it.copy(
                        isTextSelected = true
                    )
                }
            }

            else -> {}
        }

    }
}