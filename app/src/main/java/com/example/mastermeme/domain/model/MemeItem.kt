package com.example.mastermeme.domain.model

import kotlinx.serialization.Serializable

sealed interface MemeItem {

    @Serializable
    data class Template(
        val id: Int? = null,
        val imageUri : String
    ) : MemeItem
}