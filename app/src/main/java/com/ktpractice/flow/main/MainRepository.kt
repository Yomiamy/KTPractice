package com.ktpractice.flow.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Room
import com.example.test.api.ApiInstMgr
import com.ktpractice.R
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.db.PersonDao
import com.ktpractice.db.PersonDb
import com.ktpractice.model.Person
import com.ktpractice.utils.ConstantUtils
import com.ktpractice.utils.ConstantUtils.Count.PERSON_LIST_PAGE_SIZE
import io.reactivex.disposables.CompositeDisposable
import kotlin.math.ceil

class MainRepository(val mCtx:Context) {

    private var mDao: PersonDao = Room.databaseBuilder(mCtx, PersonDb::class.java, ConstantUtils.AppInfo.DB_NAME).build().personDaoDao()
    private lateinit var mCurBoundaryCallback:PersonListBoundaryCallback

    fun getPersonList(teamName:String): LiveData<PagedList<Person>> {
        mCurBoundaryCallback = PersonListBoundaryCallback(mCtx, mDao)

        mCurBoundaryCallback.setTeamName(teamName)
        return LivePagedListBuilder(mDao.getPersonList(teamName), PERSON_LIST_PAGE_SIZE)
            .setBoundaryCallback(mCurBoundaryCallback)
            .build()
    }

    fun calculateNextPage(curListItemCount:Int) {
        mCurBoundaryCallback.setNextPage(ceil(curListItemCount.toDouble() / PERSON_LIST_PAGE_SIZE).toInt())
    }
}