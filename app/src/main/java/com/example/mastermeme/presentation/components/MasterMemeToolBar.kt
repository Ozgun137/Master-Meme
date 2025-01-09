@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.mastermeme.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermeme.R
import com.example.mastermeme.ui.theme.MasterMemeTheme
import com.example.mastermeme.ui.theme.MasterMemeWhite

@Composable
fun MasterMemeToolBar(
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    toolBarTitle: String = "",
) {

    TopAppBar(
        title = {
             Text(
                 modifier = Modifier.padding(
                     top = 32.dp
                 ),
                 text = toolBarTitle,
                 style = TextStyle(
                     color = MasterMemeWhite,
                     fontSize = 24.sp,
                     fontWeight = FontWeight.Medium,
                     fontFamily = FontFamily(
                         Font(
                             resId = R.font.manrope_regular
                         )
                     )
                 )
             )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.icon_back)
                    )
                }
            }
        }
    )

}

@Preview
@Composable
fun MasterMemeToolBarPreview() = MasterMemeTheme {
    MasterMemeToolBar()
}