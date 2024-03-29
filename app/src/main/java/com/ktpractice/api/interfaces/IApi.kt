package com.ktpractice.api.interfaces

import com.ktpractice.model.Person
import com.ktpractice.api.response.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {
    @GET("/catalog")
    suspend fun search(@Query("team") team:String, @Query("page") page:Int): BaseResponse<Person>
}