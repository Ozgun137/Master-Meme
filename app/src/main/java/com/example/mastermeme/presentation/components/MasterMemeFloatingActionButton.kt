package com.example.mastermeme.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mastermeme.ui.theme.MasterMemeBlack
import com.example.mastermeme.ui.theme.MasterMemeGradientFirst
import com.example.mastermeme.ui.theme.MasterMemeGradientSecond
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MasterMemeFloatingActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MasterMemeGradientFirst,
                        MasterMemeGradientSecond
                    )
                )
            ),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MasterMemeBlack
        )
    }

}


@Preview
@Composable
fun MasterMemeFloatingActionButtonPreview() = MasterMemeTheme {
    MasterMemeFloatingActionButton(
        icon = Icons.Filled.Add,
        onClick = {},
    )
}