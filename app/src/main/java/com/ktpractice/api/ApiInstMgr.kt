package com.example.test.api

import android.content.Context
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap

class ApiInstMgr {
    companion object {
        val sUrlRetroMap = ConcurrentHashMap<String , Retrofit>()
        val sGson = GsonBuilder()
            .setLenient()
            .create()

        fun <InterfaceTypeClz> getInstnace(ctx: Context, serverUrl: String, apiInterfClz: Class<InterfaceTypeClz>): InterfaceTypeClz? {
            if (!sUrlRetroMap.contains(serverUrl)) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .client(Client.getInstance(ctx))
                    .addConverterFactory(GsonConverterFactory.create(sGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                sUrlRetroMap[serverUrl] = retrofit
            }
            return sUrlRetroMap[serverUrl]?.create(apiInterfClz)
        }
    }
}