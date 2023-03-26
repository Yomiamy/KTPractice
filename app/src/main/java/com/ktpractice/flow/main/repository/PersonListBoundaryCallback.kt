package com.ktpractice.flow.main.repository

import android.content.Context
import android.util.Log
import androidx.paging.PagedList
import com.ktpractice.api.ApiInstMgr
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.db.PersonDao
import com.ktpractice.model.Person
import com.ktpractice.utils.ConstantUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PersonListBoundaryCallback(mCtx: Context, val mDao: PersonDao) :
    PagedList.BoundaryCallback<Person>() {

    companion object {
        const val TAG = "PersonListBoundaryCallback"
    }

    private var mIApi: IApi? =
        ApiInstMgr.getInstance(mCtx, ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java)
    private var mTeamName = ""
    private var mNextPage = 0

    fun setNextPage(page: Int) {
        this.mNextPage = page
    }

    fun setTeamName(teamName: String) {
        this.mTeamName = teamName
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        GlobalScope.launch {
            fetchFromRemote()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Person) {
        super.onItemAtEndLoaded(itemAtEnd)
        GlobalScope.launch {
            fetchFromRemote()
        }
    }

    private suspend fun fetchFromRemote() {
        Log.d(TAG, "$mTeamName Page = $mNextPage")
        val response = mIApi?.search(mTeamName.toLowerCase(), mNextPage)

        if (response != null && !response.results.isNullOrEmpty()) {
            ++mNextPage
            response.results!!.forEach {
                it.teamName = mTeamName
            }
            mDao.insertAll(response.results!!)
        }
    }
}
