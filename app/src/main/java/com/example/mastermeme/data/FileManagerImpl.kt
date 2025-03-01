package com.example.mastermeme.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.mastermeme.domain.FileManager
import com.example.mastermeme.domain.util.Error
import com.example.mastermeme.domain.util.FileError
import com.example.mastermeme.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class FileManagerImpl(
    private val context: Context
) : FileManager {

    override suspend fun shareFile(uri: String): Result<Unit, Error> {
        return try {
            val shareUri = Uri.parse(uri)
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, shareUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooserIntent = Intent.createChooser(shareIntent, "Share Meme")
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooserIntent)
            Result.Success(Unit)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Result.Error(FileError.FAILED_TO_SHARE)
        }
    }
}