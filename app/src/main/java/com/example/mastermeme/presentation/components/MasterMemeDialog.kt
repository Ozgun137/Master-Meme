package com.example.mastermeme.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mastermeme.R
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MasterMemeDialog(
    memeEditorDialogContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    primaryButton: @Composable RowScope.() -> Unit,
    secondaryButton: @Composable RowScope.() -> Unit,
    title: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = FontFamily(
                            Font(R.font.manrope_regular)
                        ),
                        fontSize = 24.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(22.dp))

                memeEditorDialogContent()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    secondaryButton()
                    primaryButton()
                }
            }
        }
    }
}

@Preview
@Composable
private fun MasterMemeDialogPreview() = MasterMemeTheme {
    MasterMemeDialog(
        onDismiss = {},
        primaryButton = {
            TextButton(
                onClick = {},
            ) {
                Text(
                    text = "Leave",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(
                            Font(R.font.manrope_regular)
                        ),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        },

        secondaryButton = {
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = "Cancel",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(
                            Font(R.font.manrope_regular)
                        ),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        },

        title = "Leave editor?",
        memeEditorDialogContent = {
            Text(
                text = "You will lose your precious meme. If you're fine with that, press ‘Leave’.",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily(
                        Font(R.font.manrope_regular)
                    ),
                    fontSize = 14.sp,
                    lineHeight = 21.86.sp,
                    fontWeight = FontWeight.Normal
                ),
            )
        }

    )
}