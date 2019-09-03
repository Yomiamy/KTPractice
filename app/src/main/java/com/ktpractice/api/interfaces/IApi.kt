package com.ktpractice.api.interfaces

import com.ktpractice.model.Person
import com.ktpractice.response.BaseResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {
    @GET("/catalog")
    fun search(@Query("team") team:String, @Query("page") page:Int): Observable<BaseResponse<Person>>
}