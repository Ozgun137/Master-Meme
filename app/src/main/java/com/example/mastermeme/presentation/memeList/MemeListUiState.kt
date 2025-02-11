package com.example.mastermeme.presentation.memeList

import com.example.mastermeme.domain.model.MemeItem.Meme
import com.example.mastermeme.domain.model.MemeItem.Template

data class MemeListUiState(
    val memes: List<Meme> = emptyList(),
    val templates: List<Template> = emptyList(),
    val shouldShowModalBottomSheet:Boolean = false,
)