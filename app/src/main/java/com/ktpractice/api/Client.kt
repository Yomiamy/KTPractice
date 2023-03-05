package com.example.test.api

import android.content.Context
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.ktpractice.BuildConfig
import com.ktpractice.utils.ConstantUtils
import com.ktpractice.utils.ConstantUtils.Network.DEFAULT_TIMEOUT
import com.ktpractice.utils.ConstantUtils.Network.HTTP_LOG_TAG
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class Client private constructor() {
    companion object {
        var sClient: OkHttpClient? = null

        fun getInstance(ctx: Context): OkHttpClient? {
            if (sClient == null) {
                val HTTP_LOGGING_INTERCEPTOR = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d(HTTP_LOG_TAG, message)
                    }
                })
                HTTP_LOGGING_INTERCEPTOR.level = HttpLoggingInterceptor.Level.BODY
                //val CHUNK_INTERCEPTOR = ChuckInterceptor(ctx).showNotification(BuildConfig.DEBUG)

                sClient = OkHttpClient().newBuilder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
//                    .addInterceptor(CHUNK_INTERCEPTOR)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build()
            }
            return sClient
        }
    }
}