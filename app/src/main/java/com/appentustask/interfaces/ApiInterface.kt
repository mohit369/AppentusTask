package com.appentustask.interfaces

import com.appentustask.model.ResponseGetModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * create interface to define the param and API type
 */

interface ApiInterface {

    @GET("v2/list")
    fun getPagingData(@Query("page") page: Int): Call<ResponseGetModel>

}