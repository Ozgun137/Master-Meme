package com.example.mastermeme.presentation.memeList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermeme.R
import com.example.mastermeme.domain.model.MemeItem
import com.example.mastermeme.ui.theme.MasterMemeOnSurface

@Composable
fun MemeTemplatesContent(
    modifier: Modifier = Modifier,
    templates: List<MemeItem.Template>,
    templateSelected : (MemeItem.Template) -> Unit
) {

    val scrollState = rememberLazyGridState()

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        Text(
            text = stringResource(R.string.choose_template),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 22.sp,
                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                color = MasterMemeOnSurface
            )
        )

        Text(
            text = stringResource(R.string.choose_template_subtext),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp,
                fontFamily = FontFamily(
                    Font(R.font.manrope_regular, FontWeight.Normal)
                ),
                color = MasterMemeOnSurface
            )
        )

        Spacer(modifier = Modifier.height(42.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = scrollState,
            content = {
                items(templates) { template ->
                    MemeGridItem(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                            .clickable {
                                templateSelected(template)
                            },
                        model = template.imageUri,
                        contentDescription = stringResource(R.string.meme_template_content_description)
                    )
                }
            }
        )
    }
}