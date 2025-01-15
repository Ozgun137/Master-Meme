package com.example.mastermeme.presentation.memeEditor

sealed interface MemeEditorAction {
    data object OnBackClicked : MemeEditorAction
}