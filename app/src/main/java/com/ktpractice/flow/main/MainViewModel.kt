package com.ktpractice.flow.main

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ktpractice.flow.main.MainFlowContract.IMainFlowView
import com.ktpractice.flow.main.MainFlowContract.IMainViewModel

class MainViewModel<T>(val mView: T): ViewModel(), IMainViewModel where T:Context, T:IMainFlowView, T:LifecycleOwner{

    private lateinit var mRepository:MainRepository
    private lateinit var mTeamNameAry:Array<String>

    override fun onCreate() {
        this.mRepository = MainRepository(mView)
        this.mTeamNameAry = mRepository.mTeamNameAry

        mView.initView()
        mView.initTabs(mTeamNameAry)
    }

    override fun initPageLoading(teamName: String) {
        mRepository.getPersonList(teamName).observe(mView, Observer {
            mView.initPageLoading(teamName, it)
        })
    }

    override fun calculateNextPage(curListItemCount: Int) {
        mRepository.calculateNextPage(curListItemCount)
    }

    override fun onPageSelected(position:Int) {
        initPageLoading(mTeamNameAry[position])
    }

    override fun onResume() {
        initPageLoading(mTeamNameAry[0])
        calculateNextPage(0)
    }

    override fun onPause() {

    }

    override fun onDestroy() {
        mRepository.onDestroy()
    }
}