package com.imformation_checker.flow.main.viewmodel

import androidx.paging.PagingData
import com.imformation_checker.model.Person

data class MainPageUiState(val teamName:String?, val personList: PagingData<Person>)
