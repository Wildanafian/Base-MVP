package com.base.myapplication.network

import com.base.myapplication.network.RequestManager.RequestFailureType

interface CallbackInterface {
    fun onRequestSuccess(requestId: Int, rawData: String?, message: String?)

    fun onRequestFailure(requestId: Int, failureType: RequestFailureType?, errorCode: String?, message: String?)

    fun onRequestSessionTimeout(requestId: Int, message: String?)
}