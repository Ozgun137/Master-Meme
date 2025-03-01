package com.example.mastermeme

import android.app.Application
import com.example.mastermeme.data.di.fileManagerModule
import com.example.mastermeme.data.di.memeDatabaseModule
import com.example.mastermeme.data.di.memeEditorDataModule
import com.example.mastermeme.data.di.memeListDataModule
import com.example.mastermeme.di.appModule
import com.example.mastermeme.presentation.di.memeEditorViewModelModule
import com.example.mastermeme.presentation.di.memeListViewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MasterMemeApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MasterMemeApplication)
            modules(
                appModule,
                fileManagerModule,
                memeDatabaseModule,
                memeEditorViewModelModule,
                memeListViewModelModule,
                memeListDataModule,
                memeEditorDataModule
            )
        }
    }
}