package com.ktpractice.flow.main.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.ktpractice.flow.main.repository.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel<T>(val context: T): ViewModel() where T:Context, T: LifecycleOwner {

    var mainPageUiState: MutableLiveData<MainPageUiState?> = MutableLiveData()

    private var mRepository: MainRepository = MainRepository(context)

    fun loadPageByTeamName(teamName: String) {
        mRepository.getPersonList(teamName).observe(context) {
            viewModelScope.launch {
                mainPageUiState.value = MainPageUiState(teamName, it)
            }
        }
    }

    fun calculateNextPage(curListItemCount: Int) {
        mRepository.calculateNextPage(curListItemCount)
    }
}