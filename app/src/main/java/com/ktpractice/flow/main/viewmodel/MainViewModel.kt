package com.ktpractice.flow.main.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.ktpractice.flow.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(context: Context): ViewModel() {

    private val mMainPageUiState: MutableStateFlow<MainPageUiState?> = MutableStateFlow(null)
    private val mRepository: MainRepository = MainRepository(context)

    val mainPageUiState:StateFlow<MainPageUiState?> = mMainPageUiState.asStateFlow()

    fun loadPageByTeamName(teamName: String) {
        viewModelScope.launch {
            mRepository.getPersonList(teamName).collectLatest {
                mMainPageUiState.value = MainPageUiState(teamName, it)
            }
        }
    }

    fun calculateNextPage(curListItemCount: Int) {
        mRepository.calculateNextPage(curListItemCount)
    }
}