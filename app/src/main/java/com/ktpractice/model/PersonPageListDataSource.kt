package com.ktpractice.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.paging.Config
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.test.api.ApiInstMgr
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.utils.ConstantUtils
import io.reactivex.schedulers.Schedulers

class PersonPageListDataSource(val mCtx: Context, val mTeamName: String) :
    PageKeyedDataSource<Int, Person>() {

    private var mIApi: IApi? = ApiInstMgr.getInstnace(mCtx, ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java)

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Person>
    ) {
        mIApi?.search(mTeamName.toLowerCase(), 0)
            ?.subscribe({ response ->
                if (response.results != null) {
                    callback.onResult(response.results!!, null, 1)
                }
            }, { error -> error.printStackTrace() })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Person>) {
        mIApi?.search(mTeamName.toLowerCase(), params.key)
            ?.subscribe({ response ->
                if (response.results != null) {
                    callback.onResult(response.results!!, params.key + 1)
                }
            }, { error -> error.printStackTrace() })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Person>) {}

    class PersonPageListDataSourceFactory(val mCtx: Context, val mTeamName: String) :
        DataSource.Factory<Int, Person>() {
        override fun create(): DataSource<Int, Person> = PersonPageListDataSource(mCtx, mTeamName)
    }

}