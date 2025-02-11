package com.example.mastermeme.domain.util

interface DataError : Error {
    enum class Local : DataError {
        DISK_FULL
    }
}
