package com.example.mastermeme.presentation.memeList

import com.example.mastermeme.domain.model.MemeItem

data class MemeListUiState(
    val memes: List<MemeUi> = emptyList(),
    val templates: List<MemeItem.Template> = emptyList(),
    val shouldShowModalBottomSheet:Boolean = false,
)