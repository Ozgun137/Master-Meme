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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mastermeme.R
import com.example.mastermeme.presentation.components.MasterMemeFloatingActionButton
import com.example.mastermeme.presentation.components.MasterMemeScaffold
import com.example.mastermeme.presentation.components.MasterMemeToolBar
import com.example.mastermeme.ui.theme.MasterMemeBlack
import com.example.mastermeme.ui.theme.MasterMemeOutline
import com.example.mastermeme.ui.theme.MasterMemeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemeListScreenRoot(
    viewModel: MemeListViewModel = koinViewModel()
) {
    val memeListState by viewModel.memeListState.collectAsStateWithLifecycle()

    MemeListScreen(memeListState = memeListState)
}

@Composable
fun MemeListScreen(
    modifier: Modifier = Modifier,
    memeListState: MemeListUiState
) {

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
                onClick = {},
                contentDescription = stringResource(R.string.create_meme)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .background(MasterMemeBlack)
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if(memeListState.memes.isNotEmpty()) {
                //Meme Content
            } else {
                MemeListEmptyContent()
            }
        }
    }
}

@Composable
fun MemeListEmptyContent() {
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


@Preview
@Composable
fun MemeListScreenPreview() = MasterMemeTheme {
    MemeListScreen(
        memeListState = MemeListUiState()
    )
}