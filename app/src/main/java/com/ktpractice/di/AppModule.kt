package com.ktpractice.di

import android.content.Context
import com.ktpractice.flow.main.repository.IMainRepository
import com.ktpractice.flow.main.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideMainRepository(context: Context): IMainRepository = MainRepository(context)
}