package com.example.mastermeme.presentation.memeEditor

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.mastermeme.presentation.memeEditor.components.TextBoxUI
import com.example.mastermeme.ui.theme.MasterMemeWhite
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class MemeEditorViewModel : ViewModel() {

    private val _memeEditorUiState = MutableStateFlow(MemeEditorUiState())
    val memeEditorUiState = _memeEditorUiState.asStateFlow()

    private var textBoxId = 0


    fun onAction(action: MemeEditorAction) {
        when (action) {
            MemeEditorAction.OnBackClicked -> {
                updateDialogVisibilityState(true)
            }

            MemeEditorAction.OnLeaveEditorDialogDismissed -> {
                updateDialogVisibilityState(false)
            }

            MemeEditorAction.OnAddTextClicked -> { createTextBox() }

            MemeEditorAction.OnApplyChangesClicked -> { applyChanges() }


            MemeEditorAction.OnCancelChangesClicked -> { cancelChanges() }


            is MemeEditorAction.OnTextPositionChanged -> {
                updateTextPosition(action.id, action.offset)
            }

            is MemeEditorAction.OnTextSelected -> { selectText(action.id) }

            is MemeEditorAction.OnSliderValueChanged -> { updateTextSize(action.sliderValue) }

            is MemeEditorAction.OnRootViewClicked -> { cancelEditingState() }

            is MemeEditorAction.OnTextDeleteClicked -> { deleteTextBox(action.id) }

            else -> {}
        }

    }

    private fun updateDialogVisibilityState(shouldShowDialog : Boolean) {
        _memeEditorUiState.update {
            it.copy(shouldShowLeaveEditorDialog = shouldShowDialog)
        }
    }

    private fun createTextBox() {
        textBoxId++
        val currentTextBoxList = _memeEditorUiState.value.textBoxList.toMutableList()

        val newTextBoxUI = TextBoxUI(
            id = textBoxId,
            text = "TAP TWICE TO EDIT",
            textPosition = Offset(0f, 0f),
            textColor = MasterMemeWhite
        )

        val updatedHistory = _memeEditorUiState.value.uiStatesHistory.toMutableMap()
        updatedHistory[textBoxId] = ArrayDeque(listOf(newTextBoxUI))

        currentTextBoxList.add(newTextBoxUI)


        _memeEditorUiState.update {
            it.copy(
                textBoxList = currentTextBoxList.toList(),
                uiStatesHistory = updatedHistory
            )
        }
    }

    private fun applyChanges() {
        _memeEditorUiState.update { currentState ->
            val selectedText = currentState.editingState
            if (selectedText != null) {
                val updatedHistory = currentState.uiStatesHistory.toMutableMap()
                val historyStack = updatedHistory.getOrPut(selectedText.id) { ArrayDeque() }
                historyStack.add(selectedText)
                updatedHistory[selectedText.id] = historyStack

                val updatedTextBoxList = currentState.textBoxList.map { textBox ->
                    if (textBox.id == selectedText.id) selectedText else textBox
                }

                currentState.copy(
                    textBoxList = updatedTextBoxList,
                    selectedText = null,
                    editingState = null,
                    uiStatesHistory = updatedHistory
                )
            } else {
                currentState
            }
        }
    }

    private fun cancelChanges() {
        _memeEditorUiState.update { currentState ->
            val selectedText = currentState.selectedText
            if (selectedText != null) {
                val historyStack = currentState.uiStatesHistory[selectedText.id]
                val lastAppliedState = if ((historyStack?.size ?: 0) > 1) {
                    historyStack?.lastOrNull()
                } else {
                    historyStack?.firstOrNull()
                }

                if (lastAppliedState != null) {
                    val updatedTextBoxList = currentState.textBoxList.map { textBox ->
                        if (textBox.id == selectedText.id) lastAppliedState else textBox
                    }
                    currentState.copy(
                        textBoxList = updatedTextBoxList,
                        selectedText = null,
                        editingState = null
                    )
                } else {
                    currentState
                }
            } else {
                currentState
            }
        }
    }

    private fun updateTextPosition(textBoxID : Int, offset: Offset) {
        _memeEditorUiState.update { currentState ->
            val updatedList = currentState.textBoxList.map { textBoxUI ->
                if (textBoxUI.id == textBoxID) {
                    textBoxUI.copy(textPosition = offset)
                } else {
                    textBoxUI
                }
            }
            currentState.copy(textBoxList = updatedList)
        }
    }

    private fun selectText(id: Int) {
        var selectedText: TextBoxUI? = null

        _memeEditorUiState.update { currentState ->
            val updatedList = currentState.textBoxList.map { textBoxUI ->
                if (textBoxUI.id == id) {
                    selectedText = textBoxUI
                    textBoxUI.copy(isSelected = true)
                } else {
                    textBoxUI.copy(isSelected = false)
                }
            }


            currentState.copy(
                textBoxList = updatedList,
                selectedText = selectedText
            )
        }
    }

    private fun updateTextSize(sliderValue : Float) {
        _memeEditorUiState.update { currentState ->
            val selectedText = currentState.selectedText
            if (selectedText != null) {
                val updatedEditingState = selectedText.copy(fontSize = sliderValue)
                currentState.copy(editingState = updatedEditingState)
            } else {
                currentState
            }
        }
    }

    private fun deleteTextBox(textBoxID: Int) {
        val currentTextBoxLists = _memeEditorUiState.value.textBoxList.toMutableList()
        currentTextBoxLists.removeIf { it.id == textBoxID}

        _memeEditorUiState.update { currentState ->
            currentState.copy(
                textBoxList = currentTextBoxLists,
                selectedText = null,
            )
        }
    }

    private fun cancelEditingState() {
        _memeEditorUiState.update { currentState ->
            val selectedText = currentState.selectedText
            val isEditing = currentState.editingState != null

            if (selectedText != null && isEditing) {
                val historyStack = currentState.uiStatesHistory[selectedText.id]
                val lastAppliedState = historyStack?.lastOrNull() ?: selectedText
                val updatedTextBoxList = currentState.textBoxList.map { textBox ->
                    if (textBox.id == selectedText.id) lastAppliedState else textBox
                }

                currentState.copy(
                    textBoxList = updatedTextBoxList,
                    selectedText = null,
                    editingState = null
                )
            } else {
                currentState
            }
        }
    }


}