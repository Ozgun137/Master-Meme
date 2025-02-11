package com.example.mastermeme.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MemeEntity")
data class MemeEntity(
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    val imageUri : String,
    val timeStamp : Long
)
