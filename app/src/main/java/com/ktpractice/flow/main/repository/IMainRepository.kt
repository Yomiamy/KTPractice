package com.ktpractice.flow.main.repository

import androidx.paging.PagedList
import com.ktpractice.model.Person
import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    fun getPersonList(teamName:String): Flow<PagedList<Person>>

    fun calculateNextPage(curListItemCount:Int)
}