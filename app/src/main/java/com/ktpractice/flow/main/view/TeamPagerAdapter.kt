package com.ktpractice.flow.main.view

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.contains
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.ktpractice.R
import com.ktpractice.model.Person

class TeamPagerAdapter(private val mCtx: Context, private val mTabTeamNameArray: Array<String>) : PagerAdapter() {

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
        return mTabTeamNameArray[position]
    }

        val index = mTabTeamNameArray.indexOf(team)
        mCurRvList = mRvList[index]
        mCurRvList.adapter = adapter
    }

    fun getCurListItemCount():Int = mCurRvList.adapter?.itemCount ?: 0
}