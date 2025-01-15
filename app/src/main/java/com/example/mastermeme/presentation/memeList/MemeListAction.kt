package com.example.mastermeme.presentation.memeList


sealed interface MemeListAction {
    data object OnCreateMemeClicked : MemeListAction
    data object OnBottomSheetDismissed : MemeListAction
}