package com.example.mastermeme.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import com.example.mastermeme.domain.SaveMemeUseCase
import com.example.mastermeme.domain.util.Error
import com.example.mastermeme.domain.util.FileError
import com.example.mastermeme.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.util.UUID

class SaveMemeUseCaseImpl(
    private val context: Context
) : SaveMemeUseCase {

    override suspend fun invoke(bitmap: Bitmap): Result<String?, Error> {
        var filePath: String? = null

        return withContext(Dispatchers.IO) {
            val fileName = "meme_${UUID.randomUUID()}.jpg"

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MasterMeme")
            }

            val contentResolver = context.contentResolver
            try {
                val imageUri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                imageUri?.let { uri->
                    filePath = uri.toString()
                    contentResolver.openOutputStream(uri)?.use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    }
                }
                Result.Success(filePath)
            } catch (exception: Exception) {
                coroutineContext.ensureActive()
                Result.Error(FileError.FAILED_TO_SAVE)
            }
        }
    }
}
