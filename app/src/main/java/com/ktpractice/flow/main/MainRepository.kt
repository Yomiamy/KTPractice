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

class MainRepository(val mCtx:Context) {

    private var mIApi: IApi? = null
    private var mDao: PersonDao
    private lateinit var mCurBoundaryCallback:PersonListBoundaryCallback
    private var mDispose: CompositeDisposable
    // Init the team name from string resources
    val mTeamNameAry: Array<String>
        get() {
            return mCtx.resources.getStringArray(R.array.team_array)
        }

    init {
        mDao = Room.databaseBuilder(mCtx, PersonDb::class.java, ConstantUtils.AppInfo.DB_NAME).build().personDaoDao()
        mDispose = CompositeDisposable()
        mIApi = ApiInstMgr.getInstnace(mCtx, ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java)
    }

    fun getPersonList(teamName:String): LiveData<PagedList<Person>> {
        mCurBoundaryCallback = PersonListBoundaryCallback(mCtx, mDao)

        mCurBoundaryCallback.setTeamName(teamName)
        return LivePagedListBuilder(mDao.getPersonList(teamName), PERSON_LIST_PAGE_SIZE)
            .setBoundaryCallback(mCurBoundaryCallback)
            .build()
    }

    fun calculateNextPage(curListItemCount:Int) {
        mCurBoundaryCallback.setNextPage(Math.ceil(curListItemCount.toDouble() / PERSON_LIST_PAGE_SIZE).toInt())
    }

    internal fun onDestroy() {
        mDispose.clear()
    }
}