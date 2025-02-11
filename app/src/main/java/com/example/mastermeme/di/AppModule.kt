package com.example.mastermeme.di

import com.example.mastermeme.MasterMemeApplication
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as MasterMemeApplication).applicationScope
    }
}