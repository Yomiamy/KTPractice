package com.ktpractice.flow.main

import androidx.paging.PagedList
import com.ktpractice.model.Person

data class MainPageUiState(val teamName:String?, val personList: PagedList<Person>?)
