package com.ktpractice.di

import androidx.room.Room
import com.ktpractice.db.MIGRATION_1_2
import com.ktpractice.db.PersonDb
import com.ktpractice.flow.main.repository.MainRepository
import com.ktpractice.flow.main.viewmodel.MainViewModel
import com.ktpractice.utils.ConstantUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModule {
    val viewModule = module {
        factory { MainViewModel(get()) }
    }

    val repositoryModule = module {
        single { MainRepository(androidContext(), get()) }
        single { Room.databaseBuilder(androidContext(), PersonDb::class.java, ConstantUtils.AppInfo.DB_NAME)
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
            .personDao() }
    }
}