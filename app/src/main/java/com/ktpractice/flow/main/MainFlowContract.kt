package com.ktpractice.flow.main

import androidx.paging.PagedList
import com.ktpractice.model.Person

object MainFlowContract {

    interface IMainFlowView {
        fun initView()
        fun initTabs(tabTeamNameArray: Array<String>)
        fun initPageLoading(team: String, personList: PagedList<Person>?)
    }

    interface IMainViewModel {
        fun onCreate()
        fun onResume()
        fun onPause()
        fun onDestroy()

        fun initPageLoading(tabName: String)
        fun onPageSelected(position: Int)
        fun calculateNextPage(curListItemCount:Int)
    }
}