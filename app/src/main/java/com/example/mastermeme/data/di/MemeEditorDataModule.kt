package com.example.mastermeme.data.di

import com.example.mastermeme.data.MemeRepositoryImpl
import com.example.mastermeme.data.SaveMemeToCacheImpl
import com.example.mastermeme.data.SaveMemeToGalleryImpl
import com.example.mastermeme.domain.MemeRepository
import com.example.mastermeme.domain.SaveMemeToCacheUseCase
import com.example.mastermeme.domain.SaveMemeToGalleryUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val memeEditorDataModule = module {
    singleOf(::SaveMemeToGalleryImpl).bind<SaveMemeToGalleryUseCase>()
    singleOf(::SaveMemeToCacheImpl).bind<SaveMemeToCacheUseCase>()
    singleOf(::MemeRepositoryImpl).bind<MemeRepository>()
}