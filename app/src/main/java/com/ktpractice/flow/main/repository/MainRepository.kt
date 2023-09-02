package com.ktpractice.flow.main.repository

import com.ktpractice.api.interfaces.IApi
import com.ktpractice.db.PersonDao

class MainRepository(mDao:PersonDao, mIApi: IApi) {

    private val personPagingSource = PersonPagingSource(mDao, mIApi)

    val pagingSource: PersonPagingSource
        get() = personPagingSource
    var searchedTeamName:String = ""
        set(value) {
            field = value
            personPagingSource.teamName = value
        }
}