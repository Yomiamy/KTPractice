package com.ktpractice.flow.main

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.content.ContextCompat
import androidx.core.view.contains
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.ktpractice.R
import com.ktpractice.model.Person

class TeamPagerAdapter(val mCtx: Context, val mTabTeamNameArray: Array<String>) : PagerAdapter() {

    private lateinit var mCurRvList: RecyclerView
    private var mRvList: ArrayList<RecyclerView> = ArrayList()

    init {
        for (i in mTabTeamNameArray.indices) {
            val rvList = RecyclerView(mCtx)
            val layoutMgr = LinearLayoutManager(mCtx)
            layoutMgr.orientation = RecyclerView.VERTICAL
            rvList.layoutManager = layoutMgr

            rvList.setBackgroundColor(ContextCompat.getColor(mCtx, R.color.black_1))
            rvList.setHasFixedSize(true)
            mRvList.add(rvList)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rvList = mRvList[position]

        if (!container.contains(rvList)) {
            // Avoid add the list duplicate
            container.addView(rvList)
        }
        return rvList
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return mTabTeamNameArray.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTabTeamNameArray.get(position)
    }

    fun addContentList(team: String, personList: PagedList<Person>?) {
        val index = mTabTeamNameArray.let {
            var index = -1

            for (key in mTabTeamNameArray) {
                ++index
                if (TextUtils.equals(key, team)) break
            }
            index
        }
        mCurRvList = mRvList.get(index)
        var adapter:PersonListAdapter = if (index == -1) PersonListAdapter(mCtx) else (mCurRvList.adapter as PersonListAdapter)
        mCurRvList.adapter = adapter
        adapter.submitList(personList)
    }

    fun getCurListItemCount():Int = mCurRvList.adapter?.itemCount ?: 0
}