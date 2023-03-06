package com.ktpractice.di

import android.content.Context
import android.util.Log
import com.ktpractice.KtPraticeApplication
import com.ktpractice.flow.main.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class SampleClz(val context: Context) {
    fun test() {
        Log.d("SampleClz", "Doing test, ${hashCode()}, context is ${context}")
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SampleModule {

    @Provides
    fun provideSampleObject(@ApplicationContext context: Context):SampleClz = SampleClz(context)
}