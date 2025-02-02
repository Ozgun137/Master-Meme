package com.example.mastermeme.presentation.memeEditor.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermeme.R
import com.example.mastermeme.ui.theme.MasterMemeOutline
import com.example.mastermeme.ui.theme.MasterMemeSecondary
import com.example.mastermeme.ui.theme.MasterMemeWhite

@Composable
fun SaveMemeModalBottomSheetItem(
    modifier: Modifier = Modifier,
    iconId : Int,
    title: String,
    description: String,
    contentDescription : String,
    onItemClick : () -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            tint = MasterMemeWhite,
            contentDescription = contentDescription
        )

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = MasterMemeSecondary
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.manrope_regular)
                ),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = TextStyle(
                    color = MasterMemeOutline
                ),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(
                    Font(R.font.manrope_regular)
                ),
                lineHeight = 16.sp
            )
        }

    }

}

