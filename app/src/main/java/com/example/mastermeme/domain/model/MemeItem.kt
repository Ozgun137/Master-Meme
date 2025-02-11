package com.example.mastermeme.domain.model

import kotlinx.serialization.Serializable

sealed interface MemeItem {

    @Serializable
    data class Meme(
        val id: Int? = null,
        val imageUri: String,
        val timeStamp : Long,
    )

    @Serializable
    data class Template(
        val id: Int? = null,
        val imageUri : String
    ) : MemeItem
}