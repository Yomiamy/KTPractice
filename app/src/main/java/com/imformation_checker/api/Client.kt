package com.example.test.api

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.imformation_checker.utils.ConstantUtils.Network.DEFAULT_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class Client private constructor() {
    companion object {
        var sClient: OkHttpClient? = null

        fun getInstance(ctx: Context): OkHttpClient? {
            if (sClient == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                //val CHUNK_INTERCEPTOR = ChuckInterceptor(ctx).showNotification(BuildConfig.DEBUG)

                sClient = OkHttpClient().newBuilder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
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