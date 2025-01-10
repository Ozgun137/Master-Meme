@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.mastermeme.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermeme.R
import com.example.mastermeme.domain.model.MemeItem
import com.example.mastermeme.presentation.memeList.MemeGridItem
import com.example.mastermeme.ui.theme.MasterMemeOnSurface
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MemeTemplatesModalBottomSheet(
    modifier: Modifier = Modifier,
    isSheetOpen: Boolean = false,
    onSheetDismissed : () ->Unit = {},
    templates: List<MemeItem.Template> = emptyList()
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
        if (isSheetOpen) {
            ModalBottomSheet(
                modifier = modifier
                    .fillMaxSize(),
                sheetState = sheetState,
                onDismissRequest = { onSheetDismissed() },
                dragHandle = {
                    val isExpanded = sheetState.currentValue == SheetValue.Expanded
                    val topPadding by animateFloatAsState(
                        targetValue = if (isExpanded) 1f else 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "padding"
                    )

                    val statusBarHeight = with(LocalDensity.current) {
                        WindowInsets.statusBars.getTop(this).toDp()
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = (statusBarHeight * topPadding))
                    ) {
                        BottomSheetDefaults.DragHandle()
                    }
                },
            ) {
                MemeTemplatesContent(
                    modifier = modifier,
                    templates = templates
                )
            }
        }
}

@Composable
fun MemeTemplatesContent(
    modifier: Modifier = Modifier,
    templates: List<MemeItem.Template>
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
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        model = template.imageUri,
                        contentDescription = stringResource(R.string.meme_template_content_description)
                    )
                }
            }
        )

    }
}

@Preview
@Composable
fun MemeTemplatesModalBottomSheetPreview() {
    MasterMemeTheme {
        MemeTemplatesModalBottomSheet(
            isSheetOpen = true
        )
    }
}