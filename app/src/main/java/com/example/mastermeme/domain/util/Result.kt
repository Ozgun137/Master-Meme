package com.example.mastermeme.domain.util

typealias FileDataError = Error

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E: FileDataError>(val error: E) : Result<Nothing,E>
}