package com.example.mastermeme.presentation.memeList

import com.example.mastermeme.domain.model.MemeItem


sealed interface MemeListAction {
    data object OnCreateMemeClicked : MemeListAction
    data object OnBottomSheetDismissed : MemeListAction
    data class OnMemeSelected(val meme: MemeItem.Meme) : MemeListAction
}