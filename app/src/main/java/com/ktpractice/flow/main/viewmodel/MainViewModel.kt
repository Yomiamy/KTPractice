package com.ktpractice.flow.main.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.ktpractice.flow.main.repository.MainRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel<T>(val context: T): ViewModel() where T:Context, T: LifecycleOwner {


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