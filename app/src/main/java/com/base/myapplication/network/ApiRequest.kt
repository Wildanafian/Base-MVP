package com.base.myapplication.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pos.rewash.model.BaseError
import com.pos.rewash.model.BaseResponse
import com.base.myapplication.network.RequestManager.RequestFailureType
import com.google.gson.Gson
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class ApiRequest (private val successStatusCode: String, private val callback: CallbackInterface, val requestId: Int) : Callback<BaseResponse?> {

    override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
        if (response.code() == 200 && response.body() != null) {
            if (response.body()!!.responseCode.equals(successStatusCode, true)) {
                if (response.body()!!.data != null) {
                    callback.onRequestSuccess(requestId, response.body()!!.data, response.body()!!.message)
                } else {
                    callback.onRequestFailure(requestId, RequestFailureType.NO_DATA_FAILURE, "1", response.body()!!.message)
                }
            } else callback.onRequestFailure(requestId, RequestFailureType.NO_DATA_FAILURE, "1", response.body()!!.message)
        } else if (response.code() == 400){
            val mError:BaseError = Gson().fromJson(response.errorBody()?.string(), BaseError::class.java)
            onRequestFailure(RequestFailureType.NO_DATA_FAILURE, call, "1" + mError.responseCode, mError.message)
        } else if (response.code() == 500){
            onRequestFailure(RequestFailureType.NO_DATA_FAILURE, call, "1" + response.code(), (response.code().toString()+" "+response.message()))
        } else {
            onRequestFailure(RequestFailureType.UNKNOWN_ERROR, call, "14" + response.code(), "Unknown Error")
        }
    }

    override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
        try {
            throw t
        } catch (e: UnknownHostException) {
            onRequestFailure(RequestFailureType.HTTP_FAILURE, call, "12" , "Tidak dapat terhubung ke Server")
        } catch (e: SSLHandshakeException) {
            onRequestFailure(RequestFailureType.HTTP_FAILURE, call, "13" , "Error SSL Handshake")
        } catch (e: UnknownError) {
            onRequestFailure(RequestFailureType.UNKNOWN_ERROR, call, "14" , t.message)
        }
    }

    private fun onRequestCanceled() {
//        LoggerHelper.info("RequestFragment is canceled.");
    }

    private fun onRequestFailure(type: RequestFailureType, call: Call<BaseResponse?>, errorCode: String, errorMessage: String?) {
        if (call.isCanceled) {
            onRequestCanceled()
        } else {
            callback.onRequestFailure(requestId, type, errorCode, errorMessage)
        }
    }
}