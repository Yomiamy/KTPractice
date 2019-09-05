package com.ktpractice.flow.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter

class TeamPagerAdapter(val mCtx:Context, val mTabTitleList:List<String>) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rvList = RecyclerView(mCtx)
        container.addView(rvList)
        return rvList
//        val txtView = TextView(mCtx)
//        txtView.text = "TextView " + position
//        container.addView(txtView)
//        return txtView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
       return mTabTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTabTitleList.get(position)
    }
}