package com.ktpractice.di

import android.content.Context
import androidx.room.Room
import com.ktpractice.db.PersonDao
import com.ktpractice.db.PersonDb
import com.ktpractice.flow.main.repository.IMainRepository
import com.ktpractice.flow.main.repository.MainRepository
import com.ktpractice.utils.ConstantUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun providePersonDao(context: Context): PersonDao = Room.databaseBuilder(context, PersonDb::class.java, ConstantUtils.AppInfo.DB_NAME).build().personDao()

    @Singleton
    @Provides
    fun provideMainRepository(context: Context, dao: PersonDao): IMainRepository = MainRepository(context, dao)
}