package com.ktpractice.api

import android.content.Context
import com.example.test.api.Client
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap

class ApiInstMgr {
    companion object {
        private val sUrlRetroMap = ConcurrentHashMap<String , Retrofit>()
        private val sGson: Gson = GsonBuilder()
            .setLenient()
            .create()

        fun <InterfaceTypeClz> getInstance(ctx: Context, serverUrl: String, apiInterfaceClz: Class<InterfaceTypeClz>): InterfaceTypeClz? {
            if (!sUrlRetroMap.contains(serverUrl)) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .client(Client.getInstance(ctx))
                    .addConverterFactory(GsonConverterFactory.create(sGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                sUrlRetroMap[serverUrl] = retrofit
            }
            return sUrlRetroMap[serverUrl]?.create(apiInterfaceClz)
        }
    }
}