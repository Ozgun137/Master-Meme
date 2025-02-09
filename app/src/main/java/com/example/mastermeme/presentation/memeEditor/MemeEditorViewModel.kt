@file:OptIn(ExperimentalComposeApi::class)

package com.example.mastermeme.presentation.memeEditor

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermeme.domain.SaveMemeUseCase
import com.example.mastermeme.domain.util.Result
import com.example.mastermeme.ui.theme.MasterMemeWhite
import dev.shreyaspatil.capturable.controller.CaptureController
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MemeEditorViewModel(
    private val saveMemeUseCase: SaveMemeUseCase
) : ViewModel() {

    private val _memeEditorUiState = MutableStateFlow(MemeEditorUiState())
    val memeEditorUiState = _memeEditorUiState.asStateFlow()

    private val eventChannel = Channel<MemeEditorEvent>()
    val events = eventChannel.receiveAsFlow()

    private var textBoxId = 0


    fun onAction(action: MemeEditorAction) {
        when (action) {
            MemeEditorAction.OnBackClicked -> {
                updateDialogVisibilityState(true)
            }

            MemeEditorAction.OnBottomSheetDismissed -> {
                updateSaveMemeSheetState(shouldShowSaveMemeSheet = false)
            }

            MemeEditorAction.OnLeaveEditorDialogDismissed -> {
                updateDialogVisibilityState(false)
            }

            MemeEditorAction.OnAddTextClicked -> {
                createTextBox()
            }

            MemeEditorAction.OnApplyChangesClicked -> {
                applyChanges()
            }


            MemeEditorAction.OnCancelChangesClicked -> {
                cancelChanges()
            }

            MemeEditorAction.OnEditTextCancelClicked -> {
                _memeEditorUiState.update {
                    it.copy(
                        editingState = null,
                        shouldShowUpdateTextDialog = false
                    )
                }
            }

            MemeEditorAction.OnSaveMemeClicked -> {
                updateSaveMemeSheetState(shouldShowSaveMemeSheet = true)
            }

            is MemeEditorAction.OnSaveToDeviceClicked -> {
                 saveMemeToDevice(action.captureController)
            }

            is MemeEditorAction.OnTextChanged -> {
                changeText(textBoxID = action.id, text = action.text)
            }

            MemeEditorAction.OnTextChangeApplied -> {
                val currentEditingText = _memeEditorUiState.value.editingState

                _memeEditorUiState.update { currentState ->
                    val updatedTextBoxList = currentState.textBoxList.map { textBox ->
                        if (textBox.id == currentEditingText?.id) currentEditingText else textBox
                    }

                    currentState.copy(
                        textBoxList = updatedTextBoxList,
                        shouldShowUpdateTextDialog = false,
                        editingState = null
                    )
                }

            }

            is MemeEditorAction.OnTextDoubleTapped -> {
                val currentText = _memeEditorUiState.value.textBoxList.find { it.id == action.id }

                _memeEditorUiState.update {
                    it.copy(
                        selectedText = null,
                        editingState = currentText,
                        shouldShowUpdateTextDialog = true
                    )
                }
            }

            is MemeEditorAction.OnTextPositionChanged -> {
                updateTextPosition(action.id, action.offset)
            }

            is MemeEditorAction.OnTextSelected -> {
                selectText(action.id)
            }

            is MemeEditorAction.OnSliderValueChanged -> {
                updateTextSize(action.sliderValue)
            }

            is MemeEditorAction.OnRootViewClicked -> {
                if (_memeEditorUiState.value.editingState != null) {
                    cancelChanges()
                }
            }

            is MemeEditorAction.OnTextDeleteClicked -> {
                deleteTextBox(action.id)
            }

            else -> {}
        }

    }

    private fun updateDialogVisibilityState(shouldShowDialog: Boolean) {
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
        _memeEditorUiState.update {
            it.copy(
                selectedText = null,
                editingState = null
            )
        }
    }

    private fun updateTextPosition(textBoxID: Int, offset: Offset) {
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

        val selectedText: TextBoxUI? = _memeEditorUiState.value.textBoxList.find { it.id == id }

        _memeEditorUiState.update {
            it.copy(
                selectedText = selectedText,
                editingState = selectedText
            )
        }
    }

    private fun updateTextSize(sliderValue: Float) {
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
        currentTextBoxLists.removeIf { it.id == textBoxID }

        _memeEditorUiState.update { currentState ->
            currentState.copy(
                textBoxList = currentTextBoxLists,
                selectedText = null,
                editingState = null
            )
        }
    }

    private fun updateSaveMemeSheetState(shouldShowSaveMemeSheet : Boolean) {
        _memeEditorUiState.update {
            it.copy(
                shouldShowSaveMemeSheet = shouldShowSaveMemeSheet,
            )
        }
    }

    private fun changeText(textBoxID: Int, text: String) {
        val currentText = _memeEditorUiState.value.textBoxList.find { it.id == textBoxID }
            ?.copy(
                text = text
            )

        _memeEditorUiState.update {
            it.copy(
                editingState = currentText
            )
        }
    }

    private fun saveMemeToDevice(captureController: CaptureController) {
        val bitmap = viewModelScope.async {
            captureController.captureAsync().await().asAndroidBitmap()
        }

        viewModelScope.launch {
            val result = saveMemeUseCase(bitmap.await())
            if(result is Result.Error) {
                  eventChannel.send(MemeEditorEvent.MemeSaveFailed)
            }

            else {
                eventChannel.send(MemeEditorEvent.MemeSaved)
            }
        }
    }
}