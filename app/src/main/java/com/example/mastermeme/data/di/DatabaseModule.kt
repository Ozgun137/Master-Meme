package com.example.mastermeme.data.di

import androidx.room.Room
import com.example.mastermeme.data.database.MemeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val memeDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MemeDatabase::class.java,
            MemeDatabase.DATABASE_NAME
        ).build()
    }

    single { get<MemeDatabase>().memeDao() }
}