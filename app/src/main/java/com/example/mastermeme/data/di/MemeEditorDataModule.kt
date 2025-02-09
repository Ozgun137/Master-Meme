package com.example.mastermeme.data.di

import com.example.mastermeme.data.SaveMemeUseCaseImpl
import com.example.mastermeme.domain.SaveMemeUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val memeEditorDataModule = module {
    singleOf(::SaveMemeUseCaseImpl).bind<SaveMemeUseCase>()
}