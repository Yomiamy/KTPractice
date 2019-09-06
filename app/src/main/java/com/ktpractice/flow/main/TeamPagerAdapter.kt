package com.ktpractice.flow.main

import android.content.Context
import android.icu.lang.UCharacter
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.ktpractice.R
import com.ktpractice.model.Person

class TeamPagerAdapter(val mCtx: Context, val mTabTeamNameArray: Array<String>) : PagerAdapter() {

    private var mAdapterMap: LinkedHashMap<String, PersonListAdapter> = LinkedHashMap()
    private var mRvList: ArrayList<RecyclerView> = ArrayList()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rvList = RecyclerView(mCtx)
        val layoutMgr = LinearLayoutManager(mCtx)
        layoutMgr.orientation = RecyclerView.VERTICAL
        rvList.layoutManager = layoutMgr

        rvList.setBackgroundColor(ContextCompat.getColor(mCtx, R.color.black_1))
        rvList.setHasFixedSize(true)
        container.addView(rvList)
        mRvList.add(rvList)
        if (position <= mAdapterMap.size - 1) {
            rvList.adapter = mAdapterMap.let {
                it.values.toTypedArray()[position]
            }
        }
        return rvList
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        mRvList.remove(`object`)
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

    fun addContentList(team: String, personList: List<Person>?) {
        val adapter = PersonListAdapter(mCtx, personList)
        mAdapterMap.put(team, adapter)

        if (mAdapterMap.size > mRvList.size) return
        val index = mAdapterMap.let {
            var index = -1

            for (key in mAdapterMap.keys) {
                ++index
                if (TextUtils.equals(key, team)) break
            }
            index
        }
        val rvList = mRvList.get(index)
        rvList.adapter = adapter
    }
}