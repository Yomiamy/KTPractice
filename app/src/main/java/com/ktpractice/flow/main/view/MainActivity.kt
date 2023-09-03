package com.ktpractice.flow.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.ktpractice.R
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ktpractice.databinding.ActivityMainBinding
import com.ktpractice.flow.main.viewmodel.MainViewModel
import com.ktpractice.utils.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModel()

    private lateinit var mBinding:ActivityMainBinding
    private lateinit var mTeamNameAry:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTeamNameAry = resources.getStringArray(R.array.team_array)
        mBinding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            viewModel = mViewModel
        }

        initView()
        initListener()
        initObserver()
        initData()
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
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.mainPageUiState.collectLatest { state ->
                    if(state == null
                        || state.teamName.isNullOrEmpty()) {
                        return@collectLatest
                    }

                    val pageAdapter = (mBinding.vpViewPager.adapter as TeamPagerAdapter)
                    pageAdapter.addContentList(state.teamName, state.personList)
                }
            }
        }
    }
}
