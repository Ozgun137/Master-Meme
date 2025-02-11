package com.example.mastermeme.presentation.memeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermeme.domain.GetTemplatesUseCase
import com.example.mastermeme.domain.MemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.mastermeme.presentation.memeList.MemeListAction.OnCreateMemeClicked
import com.example.mastermeme.presentation.memeList.MemeListAction.OnBottomSheetDismissed
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemeListViewModel(
    private val getTemplatesUseCase: GetTemplatesUseCase,
    private val repository: MemeRepository
) : ViewModel() {

    private val _memeListState = MutableStateFlow((MemeListUiState()))
    val memeListState = _memeListState.asStateFlow()
        .onStart {
            loadMemes()
            loadTemplates()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MemeListUiState()
        )

    fun onAction(action: MemeListAction) {
        when (action) {
            OnCreateMemeClicked -> {
                _memeListState.update {
                    it.copy(
                        shouldShowModalBottomSheet = true
                    )
                }
            }

            OnBottomSheetDismissed -> {
                _memeListState.update {
                    it.copy(
                        shouldShowModalBottomSheet = false
                    )
                }
            }
        }
    }


    private fun loadMemes() = viewModelScope.launch {
        repository.getMemes().collect { memes ->
            _memeListState.update {
                it.copy(
                    memes = memes
                )
            }
        }
    }

    private fun loadTemplates() = viewModelScope.launch {
        val templates = getTemplatesUseCase().getOrNull() ?: emptyList()
        _memeListState.update {
            it.copy(
                templates = templates
            )
        }
    }


}