package com.ktpractice.flow.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.test.api.ApiInstMgr
import com.google.android.material.tabs.TabLayout
import com.ktpractice.R
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.utils.ConstantUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.paging.*
import androidx.room.Room
import com.ktpractice.db.PersonDao
import com.ktpractice.db.PersonDb
import com.ktpractice.flow.main.MainFlowContract.IMainViewModel
import com.ktpractice.flow.main.MainFlowContract.IMainFlowView
import com.ktpractice.model.Person
import com.ktpractice.utils.ConstantUtils.Count.PERSON_LIST_PAGE_SIZE
import com.ktpractice.utils.Utils


class MainActivity : AppCompatActivity(), IMainFlowView {

    private lateinit var mViewPager: ViewPager
    private lateinit var mTbLayout: TabLayout
    private lateinit var mTvTitle: TextView

    private var mViewModel:IMainViewModel = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mViewModel.onCreate()
    }

    override fun initView() {
        mViewPager = vp_view_pager
        mTvTitle = tv_title
        mTbLayout = tb_tab_layout

        val labelColor = ContextCompat.getColor(this, R.color.read_1)
        Utils.setColor(mTvTitle, "RedSo", "So", labelColor)
        mTbLayout.setupWithViewPager(mViewPager)

        initListener()
    }

    private fun initListener() {
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mViewModel.onPageSelected(position)
            }
        })
    }

    override fun initPageLoading(team: String, personList: PagedList<Person>?) {
        val pageAdapter = (mViewPager.adapter as TeamPagerAdapter)

        pageAdapter.addContentList(team, personList)
        mViewModel.calculateNextPage(pageAdapter.getCurListItemCount())
    }


    override fun initTabs(tabTeamNameArray: Array<String>) {
        mViewPager.adapter = TeamPagerAdapter(this, tabTeamNameArray)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDestroy()

    }
}
