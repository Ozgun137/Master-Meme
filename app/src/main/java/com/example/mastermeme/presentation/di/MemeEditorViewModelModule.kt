package com.example.mastermeme.presentation.di

import com.example.mastermeme.presentation.memeEditor.MemeEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val memeEditorViewModelModule = module {
    viewModelOf(::MemeEditorViewModel)
}