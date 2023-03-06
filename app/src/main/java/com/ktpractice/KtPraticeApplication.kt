package com.ktpractice

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.ktpractice.di.SampleClz
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class KtPraticeApplication:Application() {

    @Inject
    lateinit var mSampleClz: SampleClz

    override fun onCreate() {
        super.onCreate()

        mSampleClz.test()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}