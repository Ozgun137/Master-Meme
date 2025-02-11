package com.example.mastermeme.domain

import com.example.mastermeme.domain.model.MemeItem
import com.example.mastermeme.domain.util.DataError
import com.example.mastermeme.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MemeRepository {
    fun getMemes(): Flow<List<MemeItem.Meme>>
    suspend fun upsertMeme(meme : MemeItem.Meme): Result<Unit, DataError.Local>
}