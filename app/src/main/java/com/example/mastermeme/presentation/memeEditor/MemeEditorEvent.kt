package com.example.mastermeme.presentation.memeEditor

sealed interface MemeEditorEvent {
    data object MemeSaved : MemeEditorEvent
    data object MemeSaveFailed : MemeEditorEvent
}