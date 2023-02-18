package com.ktpractice.flow.main.viewmodel

import androidx.paging.PagedList
import com.ktpractice.model.Person

data class MainPageUiState(val teamName:String?, val personList: PagedList<Person>?)
