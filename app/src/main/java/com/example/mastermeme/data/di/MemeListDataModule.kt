package com.example.mastermeme.data.di

import com.example.mastermeme.data.GetTemplatesUseCaseImpl
import com.example.mastermeme.domain.GetTemplatesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val memeListDataModule = module {
    singleOf(::GetTemplatesUseCaseImpl).bind<GetTemplatesUseCase>()
}