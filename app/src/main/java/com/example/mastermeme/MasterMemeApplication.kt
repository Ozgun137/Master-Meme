package com.example.mastermeme

import android.app.Application
import com.example.mastermeme.presentation.di.memeListViewModelModule
import org.koin.core.context.startKoin

class MasterMemeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                memeListViewModelModule
            )
        }
    }
}