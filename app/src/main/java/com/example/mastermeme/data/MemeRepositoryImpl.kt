package com.example.mastermeme.data

import com.example.mastermeme.data.database.dao.MemeDao
import com.example.mastermeme.data.database.mapper.toMemeEntity
import com.example.mastermeme.data.database.mapper.toMemeItem
import com.example.mastermeme.domain.MemeRepository
import com.example.mastermeme.domain.model.MemeItem
import com.example.mastermeme.domain.util.DataError
import com.example.mastermeme.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.coroutineContext

class MemeRepositoryImpl (
    private val memeDao: MemeDao
) : MemeRepository {

    override fun getMemes(): Flow<List<MemeItem.Meme>> =
        memeDao.getMemes().map { memeEntity ->
            memeEntity.map {
                it.toMemeItem()
            }
        }

    override suspend fun upsertMeme(meme: MemeItem.Meme): Result<Unit, DataError.Local> {
        return try {
            memeDao.upsertMeme(meme.toMemeEntity())
            Result.Success(Unit)
        }

        catch (exception: Exception) {
            coroutineContext.ensureActive()
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}