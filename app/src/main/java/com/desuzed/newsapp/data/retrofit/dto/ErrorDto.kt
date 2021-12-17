package com.desuzed.newsapp.data.retrofit.dto

import com.desuzed.newsapp.model.Error
import com.google.gson.annotations.SerializedName

class ErrorDto {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("code")
    var code: String = ""
    @SerializedName("message")
    var message: String = ""
}

class ErrorMapper : EntityMapper<ErrorDto, Error> {
    override fun mapFromEntity(entity: ErrorDto) =
        Error(entity.status, entity.code, entity.message)
}