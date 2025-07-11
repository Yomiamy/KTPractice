package com.imformation_checker.flow.main.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.imformation_checker.flow.main.repository.MainRepository
import com.imformation_checker.utils.ConstantUtils.Count.PERSON_LIST_PAGE_SIZE
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: MainRepository): ViewModel() {


    private val mMainPageUiState: MutableStateFlow<MainPageUiState?> = MutableStateFlow(null)

    val mainPageUiState:StateFlow<MainPageUiState?> = mMainPageUiState.asStateFlow()

    fun loadPageByTeamName(teamName: String) {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = PERSON_LIST_PAGE_SIZE)) {
                mRepository.searchedTeamName = teamName
                mRepository.pagingSource
            }.flow.cachedIn(viewModelScope).collectLatest {
                    mMainPageUiState.value = MainPageUiState(teamName, it)
                }
        }
    }
}