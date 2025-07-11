package com.imformation_checker.di

import androidx.room.Room
import com.imformation_checker.api.ApiInstMgr
import com.imformation_checker.api.interfaces.IApi
import com.imformation_checker.db.MIGRATION_1_2
import com.imformation_checker.db.PersonDb
import com.imformation_checker.flow.main.repository.MainRepository
import com.imformation_checker.flow.main.viewmodel.MainViewModel
import com.imformation_checker.utils.ConstantUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModule {
    val viewModule = module {
        factory { MainViewModel(get()) }
    }

    val repositoryModule = module {
        single { MainRepository(get(), get()) }
        single { Room.databaseBuilder(androidContext(), PersonDb::class.java, ConstantUtils.AppInfo.DB_NAME)
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
            .personDao() }
        single { ApiInstMgr.getInstance(get(), ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java) }
    }
}