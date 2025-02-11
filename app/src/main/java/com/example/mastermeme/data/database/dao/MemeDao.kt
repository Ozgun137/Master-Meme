package com.example.mastermeme.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.mastermeme.data.database.entity.MemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {

    @Upsert
    suspend fun upsertMeme(meme: MemeEntity)

    @Query("SELECT * FROM MemeEntity ORDER BY timeStamp DESC")
    fun getMemes(): Flow<List<MemeEntity>>
}