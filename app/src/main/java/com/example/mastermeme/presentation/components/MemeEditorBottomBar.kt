package com.example.mastermeme.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MemeEditorBottomBar(
    modifier: Modifier = Modifier,
    bottomBarContent: @Composable RowScope.() ->Unit,
) {

     BottomAppBar (
          modifier = modifier
     ) {
          bottomBarContent()
     }

}

@Preview
@Composable
private fun MemeEditorBottomBarPreview() = MasterMemeTheme {
    MemeEditorBottomBar(
        bottomBarContent = {}
    )
}



