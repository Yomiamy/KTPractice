package com.ktpractice.flow.main.repository

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.db.PersonDao
import com.ktpractice.model.Person
import com.ktpractice.utils.ConstantUtils.Count.PERSON_LIST_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlin.math.ceil

class MainRepository(private val mDao:PersonDao,
                     private val mIApi: IApi):IMainRepository {

    private lateinit var mCurBoundaryCallback: PersonListBoundaryCallback

    override fun getPersonList(teamName:String): Flow<PagedList<Person>> {
        mCurBoundaryCallback = PersonListBoundaryCallback(mDao, mIApi)

        mCurBoundaryCallback.setTeamName(teamName)
        return LivePagedListBuilder(mDao.getPersonList(teamName), PERSON_LIST_PAGE_SIZE)
            .setBoundaryCallback(mCurBoundaryCallback)
            .build().asFlow()
    }

    override fun calculateNextPage(curListItemCount:Int) {
        mCurBoundaryCallback.setNextPage(ceil(curListItemCount.toDouble() / PERSON_LIST_PAGE_SIZE).toInt())
    }
}