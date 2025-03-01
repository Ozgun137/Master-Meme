package com.example.mastermeme.presentation.memeList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.mastermeme.presentation.components.MasterMemeModalBottomSheet
import com.example.mastermeme.presentation.memeList.components.MemeGridItem
import com.example.mastermeme.presentation.memeList.components.MemeTemplatesContent
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
        onAction = { action ->
            when(action) {
                is MemeListAction.OnMemeSelected -> {
                    onMemeSelected(action.meme.imageUri)
                }
                else -> viewModel.onAction(action)
            }

        },
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
    onAction: (MemeListAction) -> Unit,
    templateSelected: (MemeItem.Template) -> Unit
) {

    if (memeListState.shouldShowModalBottomSheet) {
        MasterMemeModalBottomSheet(
            isSheetOpen = true,
            onSheetDismissed = {
                onAction(MemeListAction.OnBottomSheetDismissed)
            },
            modalBottomSheetContent = {
                MemeTemplatesContent(
                    modifier = modifier,
                    templates = memeListState.templates,
                    templateSelected = {
                        templateSelected(it)
                    },
                )
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
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MasterMemeBlack)
                .padding(padding)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        ) {
            if (memeListState.memes.isNotEmpty()) {
                MemeListContent(
                    state = memeListState,
                    onMemeSelected = { meme ->
                        onAction(MemeListAction.OnMemeSelected(meme))
                    }
                )
            } else {
                MemeListEmptyContent()
            }
        }
    }
}

@Composable
private fun MemeListEmptyContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
}

@Composable
private fun MemeListContent(
    state: MemeListUiState,
    onMemeSelected: (MemeItem.Meme) -> Unit,
) {
    val scrollState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        state = scrollState,
        content = {
            items(state.memes.size) { index ->
                MemeGridItem(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                        .clickable {
                            onMemeSelected(state.memes[index])
                        },
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