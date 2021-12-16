package com.desuzed.newsapp.data.retrofit.dto

import com.google.gson.annotations.SerializedName

class ErrorDto {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("code")
    var code: String = ""
    @SerializedName("message")
    var message: String = ""
}