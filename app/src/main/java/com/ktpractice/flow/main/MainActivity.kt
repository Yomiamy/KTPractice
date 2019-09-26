package com.ktpractice.flow.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.test.api.ApiInstMgr
import com.google.android.material.tabs.TabLayout
import com.ktpractice.R
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.model.Person
import com.ktpractice.response.BaseResponse
import com.ktpractice.utils.ConstantUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import android.text.Html
import androidx.core.content.ContextCompat
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.paging.toObservable
import com.ktpractice.model.PersonPageListDataSource
import com.ktpractice.utils.ConstantUtils.Count.PERSON_LIST_PAGE_PREFETCH_DIST
import com.ktpractice.utils.ConstantUtils.Count.PERSON_LIST_PAGE_SIZE
import com.ktpractice.utils.Utils
import io.reactivex.Observable


class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var mTbLayout: TabLayout
    private lateinit var mTvTitle: TextView

    //private lateinit var mViewModel:MainViewModel
    private var mIApi: IApi? = null
    private lateinit var mDispose: CompositeDisposable
    private lateinit var mTeamNameAry: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initView()
        initListener()
        init()
    }

    private fun initView() {
        mViewPager = vp_view_pager
        mTvTitle = tv_title
        mTbLayout = tb_tab_layout

        val labelColor = ContextCompat.getColor(this, R.color.read_1)
        Utils.setColor(mTvTitle, "RedSo", "So", labelColor)
        mTbLayout.setupWithViewPager(mViewPager)
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
                initPageLoading(mTeamNameAry[position])
            }
        })
    }

    private fun initPageLoading(teamName: String) {
        val dataSourceFact =
            PersonPageListDataSource.PersonPageListDataSourceFactory(this, teamName)
        val pagedListConfig =
            PagedList.Config.Builder().setPageSize(PERSON_LIST_PAGE_SIZE).setPrefetchDistance(PERSON_LIST_PAGE_PREFETCH_DIST).build()

        dataSourceFact.toObservable(
            config = pagedListConfig,
            fetchScheduler = Schedulers.io(),
            notifyScheduler = AndroidSchedulers.mainThread()
        ).subscribe {
            (mViewPager.adapter as TeamPagerAdapter).addContentList(teamName, it)
        }.apply { mDispose.add(this) }
    }

    private fun init() {
        mTeamNameAry = resources.getStringArray(R.array.team_array)
        mDispose = CompositeDisposable()
        mIApi = ApiInstMgr.getInstnace(this, ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java)
        mViewPager.adapter = TeamPagerAdapter(this, mTeamNameAry)

        initPageLoading(mTeamNameAry[0])
    }

    override fun onDestroy() {
        super.onDestroy()
        //mViewModel.onDestroy()
        mDispose.clear()
    }
}
