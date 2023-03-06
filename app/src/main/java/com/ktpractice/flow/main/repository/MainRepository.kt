package com.ktpractice.flow.main.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Room
import com.ktpractice.db.PersonDao
import com.ktpractice.db.PersonDb
import com.ktpractice.model.Person
import com.ktpractice.utils.ConstantUtils
import com.ktpractice.utils.ConstantUtils.Count.PERSON_LIST_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlin.math.ceil

class MainRepository(private val mCtx:Context):IMainRepository {

    private var mDao: PersonDao = Room.databaseBuilder(mCtx, PersonDb::class.java, ConstantUtils.AppInfo.DB_NAME).build().personDaoDao()
    private lateinit var mCurBoundaryCallback: PersonListBoundaryCallback

    override fun getPersonList(teamName:String): Flow<PagedList<Person>> {
        mCurBoundaryCallback = PersonListBoundaryCallback(mCtx, mDao)

        mCurBoundaryCallback.setTeamName(teamName)
        return LivePagedListBuilder(mDao.getPersonList(teamName), PERSON_LIST_PAGE_SIZE)
            .setBoundaryCallback(mCurBoundaryCallback)
            .build().asFlow()
    }

    override fun calculateNextPage(curListItemCount:Int) {
        mCurBoundaryCallback.setNextPage(ceil(curListItemCount.toDouble() / PERSON_LIST_PAGE_SIZE).toInt())
    }
}