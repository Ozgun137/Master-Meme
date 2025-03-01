package com.example.mastermeme.data.di

import com.example.mastermeme.data.FileManagerImpl
import com.example.mastermeme.domain.FileManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val fileManagerModule = module {
    singleOf(::FileManagerImpl).bind<FileManager>()
}