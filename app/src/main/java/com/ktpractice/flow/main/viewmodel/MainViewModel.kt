package com.ktpractice.flow.main.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.ktpractice.flow.main.repository.MainRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: MainRepository): ViewModel() {


    private val mMainPageUiState: MutableStateFlow<MainPageUiState?> = MutableStateFlow(null)

    val mainPageUiState:StateFlow<MainPageUiState?> = mMainPageUiState.asStateFlow()

    fun loadPageByTeamName(teamName: String) {
        viewModelScope.launch {

    }
}