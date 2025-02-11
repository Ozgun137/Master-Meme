package com.example.mastermeme.data.database.mapper

import com.example.mastermeme.data.database.entity.MemeEntity
import com.example.mastermeme.domain.model.MemeItem

fun MemeEntity.toMemeItem() = MemeItem.Meme(
    id = id ?: -1,
    timeStamp = timeStamp,
    imageUri = imageUri
)

fun MemeItem.Meme.toMemeEntity() = MemeEntity(
    timeStamp = timeStamp,
    imageUri = imageUri
)