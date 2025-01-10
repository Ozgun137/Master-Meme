package com.example.mastermeme

import android.app.Application
import com.example.mastermeme.data.di.memeListDataModule
import com.example.mastermeme.presentation.di.memeListViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MasterMemeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MasterMemeApplication)
            modules(
                memeListViewModelModule,
                memeListDataModule,
            )
        }
    }
}