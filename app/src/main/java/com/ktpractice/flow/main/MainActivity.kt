package com.ktpractice.flow.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager:ViewPager
    private lateinit var mTbLayout:TabLayout

    private lateinit var mViewModel:MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initView()
        init()
    }

    private fun initView() {
        mViewPager = vp_view_pager
        mTbLayout = tb_tab_layout
    }

    private fun init() {
        val titleList = ArrayList<String>()
        val teamAry = resources.getStringArray(R.array.team_array)

        for(team in teamAry) {
            val tab = tb_tab_layout.newTab()
            tab.text = team

            tb_tab_layout.addTab(tab)
        }
        titleList.addAll(teamAry)
        mViewPager.adapter = TeamPagerAdapter(this, titleList)

        mTbLayout.setupWithViewPager(mViewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDestroy()
    }
}
