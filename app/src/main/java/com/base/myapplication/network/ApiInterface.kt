package com.base.myapplication.network

import com.pos.rewash.model.BaseResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST(ApiEndPoint.VERSION)
    fun getLogin(@Field("data") data: String?): Call<BaseResponse>
}