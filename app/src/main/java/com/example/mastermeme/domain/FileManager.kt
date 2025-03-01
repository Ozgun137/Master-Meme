package com.example.mastermeme.domain

import com.example.mastermeme.domain.util.Error
import com.example.mastermeme.domain.util.Result

interface FileManager {
    suspend fun shareFile(uri : String) : Result<Unit, Error>
}