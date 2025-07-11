package com.imformation_checker.api.interfaces

import com.imformation_checker.model.Person
import com.imformation_checker.api.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {
    @GET("/catalog")
    suspend fun search(@Query("team") team:String, @Query("page") page:Int): BaseResponse<Person>
}