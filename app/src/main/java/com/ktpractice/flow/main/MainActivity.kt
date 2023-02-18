package com.ktpractice.flow.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.ktpractice.R
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.ktpractice.databinding.ActivityMainBinding
import com.ktpractice.utils.Utils


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding:ActivityMainBinding
    private lateinit var mViewModel: MainViewModel<*>
    private lateinit var mTeamNameAry:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTeamNameAry = resources.getStringArray(R.array.team_array)
        mViewModel = ViewModelProviders.of(this, MainViewModelFactory(this)).get(MainViewModel::class.java)
        mBinding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            viewModel = mViewModel
        }

        initView()
        initListener()
        initData()
        initObserver()
    }

    private fun initView() {
        val labelColor = ContextCompat.getColor(this, R.color.read_1)
        Utils.setColor(mBinding.tvTitle, "RedSo", "So", labelColor)

        mBinding.vpViewPager.adapter = TeamPagerAdapter(this, mTeamNameAry)
        mBinding.tbTabLayout.setupWithViewPager(mBinding.vpViewPager)
    }

    private fun initListener() {
        mBinding.vpViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) = mViewModel.loadPageByTeamName(mTeamNameAry[position])
        })
    }

    private fun initData() {
        mViewModel.loadPageByTeamName(mTeamNameAry[0])
        mViewModel.calculateNextPage(0)
    }

    private fun initObserver() {
        mViewModel.mainPageUiState.observe(this) { state ->
            if(state == null
                || state.teamName.isNullOrEmpty()
                || state.personList.isNullOrEmpty()) {
                return@observe
            }

            val pageAdapter = (mBinding.vpViewPager.adapter as TeamPagerAdapter)
            pageAdapter.addContentList(state.teamName, state.personList)
            mViewModel.calculateNextPage(pageAdapter.getCurListItemCount())
        }
    }
}
