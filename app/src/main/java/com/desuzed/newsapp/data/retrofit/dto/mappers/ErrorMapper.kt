package com.desuzed.newsapp.data.retrofit.dto.mappers

import com.desuzed.newsapp.data.retrofit.dto.ErrorDto
import com.desuzed.newsapp.model.Error

class ErrorMapper : EntityMapper<ErrorDto, Error> {
    override fun mapFromEntity(entity: ErrorDto) =
        Error(entity.status, entity.code, entity.message)
}