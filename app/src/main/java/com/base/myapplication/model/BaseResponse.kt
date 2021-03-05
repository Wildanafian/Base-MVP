package com.pos.rewash.model

import com.google.gson.annotations.SerializedName

class BaseResponse {
    @SerializedName("response_code")
    var responseCode: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: String? = null
}