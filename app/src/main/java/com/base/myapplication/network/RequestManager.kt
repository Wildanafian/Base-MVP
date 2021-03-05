package com.base.myapplication.network

import android.util.Log
import retrofit2.Call
import com.pos.rewash.model.BaseResponse

object RequestManager {
    
    fun login(callback: CallbackInterface, data: String, requestId: Int): Call<BaseResponse> {
        logger("login",data)
        return ApiClient().services.getLogin(data).applyEnqueue(callback,requestId)
    }

    enum class RequestFailureType {
        SERVICE_FAILURE, HTTP_FAILURE, NETWORK_FAILURE, NO_DATA_FAILURE, INPUT_FAILURE, UNKNOWN_ERROR
    }

    private fun logger (name: String?, value: String?){
        Log.d("###hit_$name", value!!)
    }

    private fun <T: Call<BaseResponse>> T.applyEnqueue(callback: CallbackInterface, requestId: Int) = this.apply { enqueue(
        ApiRequest("00", callback, requestId)
    ) }

}