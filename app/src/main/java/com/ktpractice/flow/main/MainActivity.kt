package com.ktpractice.flow.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.test.api.ApiInstMgr
import com.google.android.material.tabs.TabLayout
import com.ktpractice.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.paging.*
import com.ktpractice.databinding.ActivityMainBinding
import com.ktpractice.flow.main.MainFlowContract.IMainViewModel
import com.ktpractice.flow.main.MainFlowContract.IMainFlowView
import com.ktpractice.model.Person
import com.ktpractice.utils.Utils


class MainActivity : AppCompatActivity(), IMainFlowView {

    private lateinit var mBinding:ActivityMainBinding
    private lateinit var mViewModel: MainViewModel<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this, MainViewModelFactory(this)).get(MainViewModel::class.java)
        mViewModel.onCreate()
    }

    override fun initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        val labelColor = ContextCompat.getColor(this, R.color.read_1)
        Utils.setColor(mBinding.tvTitle, "RedSo", "So", labelColor)
        mBinding.tbTabLayout.setupWithViewPager(mBinding.vpViewPager)

        initListener()
    }

    private fun initListener() {
        mBinding.vpViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        val pageAdapter = (mBinding.vpViewPager.adapter as TeamPagerAdapter)

        pageAdapter.addContentList(team, personList)
        mViewModel.calculateNextPage(pageAdapter.getCurListItemCount())
    }


    override fun initTabs(tabTeamNameArray: Array<String>) {
        mBinding.vpViewPager.adapter = TeamPagerAdapter(this, tabTeamNameArray)
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
