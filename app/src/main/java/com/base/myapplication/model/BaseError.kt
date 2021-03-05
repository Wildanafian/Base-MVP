package com.pos.rewash.model

import com.google.gson.annotations.SerializedName

class BaseError {
    @SerializedName("response_code")
    var responseCode: String? = null

    @SerializedName("message")
    var message: String? = null
}