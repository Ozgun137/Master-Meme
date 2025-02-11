package com.example.mastermeme.presentation.memeList

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.mastermeme.domain.model.MemeItem.Meme


class MemeListPreviewParameterProvider : PreviewParameterProvider<Meme> {

    override val values = sequenceOf(
        Meme(
            id = 1,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 2,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 3,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 4,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 5,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 6,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 7,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 8,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 9,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
        Meme(
            id = 10,
            imageUri = "android.resource://com.example.mastermeme/drawable/vt4i_27",
            timeStamp = System.currentTimeMillis()
        ),
    )

}