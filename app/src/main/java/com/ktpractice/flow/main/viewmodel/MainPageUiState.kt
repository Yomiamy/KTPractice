package com.ktpractice.flow.main.viewmodel

import androidx.paging.PagingData
import com.ktpractice.model.Person

data class MainPageUiState(val teamName:String?, val personList: PagingData<Person>)
