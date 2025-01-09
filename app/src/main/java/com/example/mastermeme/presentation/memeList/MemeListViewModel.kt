package com.example.mastermeme.presentation.memeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class MemeListViewModel : ViewModel() {

    private val _memeListState = MutableStateFlow((MemeListUiState()))
    val memeListState = _memeListState.asStateFlow()
        .onStart {
            loadMemes()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MemeListUiState()
        )


     private fun loadMemes() {

     }

     private fun loadTemplates() {

     }






}