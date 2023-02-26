package com.ktpractice.flow.main.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.ktpractice.flow.main.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel<T>(val context: T): ViewModel() where T:Context, T: LifecycleOwner {


    private val mMainPageUiState: MutableStateFlow<MainPageUiState?> = MutableStateFlow(null)
    private val mRepository: MainRepository = MainRepository(context)

    val mainPageUiState:StateFlow<MainPageUiState?> = mMainPageUiState.asStateFlow()

    fun loadPageByTeamName(teamName: String) {
        mRepository.getPersonList(teamName).observe(context) {
            viewModelScope.launch {
                mMainPageUiState.value = MainPageUiState(teamName, it)
            }
        }
    }

    fun calculateNextPage(curListItemCount: Int) {
        mRepository.calculateNextPage(curListItemCount)
    }
}