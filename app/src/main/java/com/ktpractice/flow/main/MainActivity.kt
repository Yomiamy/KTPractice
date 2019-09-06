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
import com.ktpractice.utils.Utils


class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager:ViewPager
    private lateinit var mTbLayout:TabLayout
    private lateinit var mTvTitle:TextView

    //private lateinit var mViewModel:MainViewModel
    private lateinit var mTeamNameAry:Array<String>
    private var mIApi: IApi? = null
    private lateinit var mDispose: CompositeDisposable


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
        mViewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                search(mTeamNameAry[position])
            }
        })
    }

    private fun init() {
        mTeamNameAry = resources.getStringArray(R.array.team_array)
        mDispose = CompositeDisposable()
        mIApi = ApiInstMgr.getInstnace(this, ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java)

        for(team in mTeamNameAry) {
            val tab = tb_tab_layout.newTab()
            tab.text = team

            tb_tab_layout.addTab(tab)
        }
        mViewPager.adapter = TeamPagerAdapter(this, mTeamNameAry)
        search(mTeamNameAry[0])
    }

    fun search(team:String) {
        mIApi?.search(team.toLowerCase(), 0)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResponse<Person>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    mDispose.add(d)
                }

                override fun onNext(response: BaseResponse<Person>) {
                    (mViewPager.adapter as TeamPagerAdapter).addContentList(team, response.results)
                    Log.d("Test", response.results?.toString() + " : " + response.results?.get(0)?.name)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        //mViewModel.onDestroy()
        mDispose.clear()
    }
}
