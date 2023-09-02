package com.ktpractice.flow.main.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ktpractice.flow.main.repository.MainRepository
import com.ktpractice.utils.ConstantUtils.Count.PERSON_LIST_PAGE_SIZE
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: MainRepository): ViewModel() {


    private val mMainPageUiState: MutableStateFlow<MainPageUiState?> = MutableStateFlow(null)

    val mainPageUiState:StateFlow<MainPageUiState?> = mMainPageUiState.asStateFlow()

    fun loadPageByTeamName(teamName: String) {
        viewModelScope.launch {
            mRepository.searchedTeamName = teamName

            Pager(PagingConfig(pageSize = PERSON_LIST_PAGE_SIZE)) { mRepository.pagingSource }
                .flow
                .cachedIn(viewModelScope).collectLatest {
                    mMainPageUiState.value = MainPageUiState(teamName, it)
                }
        }
    }
}