package com.ktpractice.flow.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.Room
import com.example.test.api.ApiInstMgr
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.db.PersonDao
import com.ktpractice.db.PersonDb
import com.ktpractice.model.Person
import com.ktpractice.utils.ConstantUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.MutableLiveData


class PersonListBoundaryCallback(mCtx: Context, val mDao:PersonDao) :
    PagedList.BoundaryCallback<Person>() {

    private var mIApi: IApi? =
        ApiInstMgr.getInstnace(mCtx, ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java)
    private val mDispose: CompositeDisposable = CompositeDisposable()
    private var mTeamName = ""
    private var mNextPage = 0

    fun setNextPage(page:Int) {
        this.mNextPage = page
    }

    fun setTeamName(teamName: String) {
        this.mTeamName = teamName
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchFromRemote()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Person) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        mIApi?.search(mTeamName.toLowerCase(), mNextPage)
            ?.subscribeOn(Schedulers.io())
            ?.map { response ->
                if (response.results != null) {
                    ++mNextPage
                    response.results!!.forEach {
                        it.teamName = mTeamName
                    }
                    mDao.insertAll(response.results!!)
                }
                response.results
            }
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doFinally {
                mDispose.clear()
            }
            ?.subscribe({}, { error -> error.printStackTrace() })
            .apply {
                if (this == null) return

                mDispose.add(this)
            }
    }
}
