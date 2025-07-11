package com.imformation_checker.flow.main.repository

import com.imformation_checker.api.interfaces.IApi
import com.imformation_checker.db.PersonDao

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