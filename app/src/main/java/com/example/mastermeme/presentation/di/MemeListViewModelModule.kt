package com.example.mastermeme.presentation.di

import com.example.mastermeme.presentation.memeList.MemeListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val memeListViewModelModule = module {
    viewModelOf(::MemeListViewModel)
}