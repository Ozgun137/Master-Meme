package com.example.mastermeme.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mastermeme.data.database.dao.MemeDao
import com.example.mastermeme.data.database.entity.MemeEntity

@Database(entities = [MemeEntity::class], version = 1)
abstract class MemeDatabase : RoomDatabase(){
    abstract fun memeDao(): MemeDao

    companion object {
        const val DATABASE_NAME = "meme_database"
    }
}