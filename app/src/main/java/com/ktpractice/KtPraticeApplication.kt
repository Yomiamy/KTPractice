package com.ktpractice

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.ktpractice.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KtPraticeApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@KtPraticeApplication)
            modules(AppModule.viewModule, AppModule.repositoryModule)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}