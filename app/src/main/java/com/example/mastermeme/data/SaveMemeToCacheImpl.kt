package com.example.mastermeme.data

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.example.mastermeme.domain.SaveMemeToCacheUseCase
import com.example.mastermeme.domain.util.Error
import com.example.mastermeme.domain.util.FileError
import com.example.mastermeme.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID


class SaveMemeToCacheImpl(
    private val context: Context
) : SaveMemeToCacheUseCase {

    override suspend fun invoke(bitmap:Bitmap): Result<String?, Error> {
        return withContext(Dispatchers.IO) {
            try {
                val fileName = "meme_${UUID.randomUUID()}.png"
                val cacheFile = File(context.cacheDir, fileName)

                cacheFile.outputStream().use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }

                val fileUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    cacheFile
                )

                Result.Success(fileUri.toString())
            }

            catch (exception : Exception) {
                coroutineContext.ensureActive()
                Result.Error(FileError.FAILED_TO_SAVE)
            }
        }
    }

}