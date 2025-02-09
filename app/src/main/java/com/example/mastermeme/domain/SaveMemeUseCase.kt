package com.example.mastermeme.domain

import android.graphics.Bitmap
import com.example.mastermeme.domain.util.Error
import com.example.mastermeme.domain.util.Result

fun interface SaveMemeUseCase : suspend (Bitmap) -> Result<Unit,Error>