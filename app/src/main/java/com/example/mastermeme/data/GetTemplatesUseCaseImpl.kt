package com.example.mastermeme.data

import android.content.Context
import com.example.mastermeme.domain.GetTemplatesUseCase
import com.example.mastermeme.domain.model.MemeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

class GetTemplatesUseCaseImpl(
    private val context: Context
) : GetTemplatesUseCase {

    override suspend fun invoke(): Result<List<MemeItem.Template>> {
        return withContext(Dispatchers.IO) {
            try {
                val assetManager = context.assets
                val fileNames = assetManager.list("")?.filter { it.endsWith(".webp") }

                val memeTemplates = fileNames?.map {  fileName ->
                    MemeItem.Template(
                        imageUri = "file:///android_asset/$fileName"
                    )
                } ?: emptyList()

                Result.success(memeTemplates)
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                Result.failure(e)
            }
        }
    }
}