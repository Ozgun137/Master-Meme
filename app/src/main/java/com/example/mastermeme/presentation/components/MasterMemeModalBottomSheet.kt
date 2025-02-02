@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.mastermeme.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MasterMemeModalBottomSheet(
    modifier: Modifier = Modifier,
    isSheetOpen: Boolean = false,
    onSheetDismissed : () ->Unit = {},
    modalBottomSheetContent : @Composable ColumnScope.() -> Unit,
    modalBottomSheetHeightRatio : Float = 1f
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

        if (isSheetOpen) {
            ModalBottomSheet(
                modifier = modifier
                    .fillMaxHeight(modalBottomSheetHeightRatio),
                sheetState = sheetState,
                onDismissRequest = { onSheetDismissed() },
                dragHandle = {
                    Box {
                        BottomSheetDefaults.DragHandle()
                    }
                },
            ) {
                modalBottomSheetContent()
            }
        }
}



@Preview
@Composable
fun MemeTemplatesModalBottomSheetPreview() {
    MasterMemeTheme {
        MasterMemeModalBottomSheet(
            isSheetOpen = true,
            modalBottomSheetContent = {}
        )
    }
}