package com.example.mastermeme.presentation.memeList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mastermeme.R
import com.example.mastermeme.domain.model.MemeItem
import com.example.mastermeme.presentation.components.MasterMemeFloatingActionButton
import com.example.mastermeme.presentation.components.MasterMemeScaffold
import com.example.mastermeme.presentation.components.MasterMemeToolBar
import com.example.mastermeme.presentation.components.MemeTemplatesModalBottomSheet
import com.example.mastermeme.ui.theme.MasterMemeBlack
import com.example.mastermeme.ui.theme.MasterMemeOutline
import com.example.mastermeme.ui.theme.MasterMemeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemeListScreenRoot(
    viewModel: MemeListViewModel = koinViewModel(),
    onMemeSelected: (String) -> Unit
) {
    val memeListState by viewModel.memeListState.collectAsStateWithLifecycle()

    MemeListScreen(
        memeListState = memeListState,
        onAction = viewModel::onAction,
        templateSelected = {
            onMemeSelected(it.imageUri)
            viewModel.onAction(MemeListAction.OnBottomSheetDismissed)
        }
    )
}

@Composable
fun MemeListScreen(
    modifier: Modifier = Modifier,
    memeListState: MemeListUiState,
    onAction : (MemeListAction) -> Unit,
    templateSelected : (MemeItem.Template) -> Unit
) {

    if(memeListState.shouldShowModalBottomSheet) {
        MemeTemplatesModalBottomSheet(
            isSheetOpen = true,
            onSheetDismissed = {
                onAction(MemeListAction.OnBottomSheetDismissed)
            },
            templates = memeListState.templates,
            templateSelected = {
                templateSelected(it)
            }
        )
    }

    MasterMemeScaffold(
        topAppBar = {
            MasterMemeToolBar(
                toolBarTitle = stringResource(R.string.your_memes),
            )
        },
        floatingActionButton = {
            MasterMemeFloatingActionButton(
                modifier = Modifier.padding(21.dp),
                icon = Icons.Default.Add,
                onClick = {
                    onAction(MemeListAction.OnCreateMemeClicked)
                },
                contentDescription = stringResource(R.string.create_meme)
            )
        }
    ) { padding->
        Box(
            modifier = modifier.
                 fillMaxSize()
                .background(MasterMemeBlack)
                .padding(padding)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if(memeListState.memes.isNotEmpty()) {
                MemeListContent(state = memeListState)
            } else {
                MemeListEmptyContent()
            }
        }
    }
}

@Composable
private fun MemeListEmptyContent() {
       Column (
           modifier = Modifier.fillMaxWidth(),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
          Image(
              painter = painterResource(R.drawable.empty_meme),
              contentDescription = ""
          )

          Spacer(modifier = Modifier.height(32.dp))

          Text(
              text = stringResource(R.string.create_your_first_meme),
              style = TextStyle(
                  fontSize = 14.sp,
                  lineHeight = 20.sp,
                  fontWeight = FontWeight.Normal,
                  color = MasterMemeOutline
              )
          )
       }
}

@Composable
private fun MemeListContent(state: MemeListUiState) {
    val scrollState = rememberLazyGridState()
   LazyVerticalGrid(
       columns = GridCells.Fixed(2),
       state = scrollState,
       content = {
           items(state.memes.size) { index->
               MemeGridItem(
                   modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                   model = state.memes[index].imageUri,
                   contentDescription = stringResource(R.string.meme_content_description)
               )
           }
       }
   )
}


@Preview
@Composable
private fun MemeListScreenPreview() = MasterMemeTheme {
    val memesList = MemeListPreviewParameterProvider().values.toList()
    MemeListScreen(
        memeListState = MemeListUiState(
           memes = memesList
        ),
        onAction = {},
        templateSelected = {}
    )
}