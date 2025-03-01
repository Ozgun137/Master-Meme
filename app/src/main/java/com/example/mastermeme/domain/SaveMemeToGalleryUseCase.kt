package com.example.mastermeme.domain

import android.graphics.Bitmap
import com.example.mastermeme.domain.util.Error
import com.example.mastermeme.domain.util.Result

fun interface SaveMemeToGalleryUseCase : suspend (Bitmap) -> Result<String?,Error>