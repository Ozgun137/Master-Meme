package com.example.mastermeme.domain

import android.graphics.Bitmap
import com.example.mastermeme.domain.util.Error
import com.example.mastermeme.domain.util.Result

interface SaveMemeToCacheUseCase : suspend (Bitmap) -> Result<String?, Error>