package com.desuzed.newsapp.data.retrofit.dto

import com.desuzed.newsapp.model.Source
import com.google.gson.annotations.SerializedName


data class SourceDto(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null
)

class SourceMapper : EntityMapper<SourceDto, Source> {
    override fun mapFromEntity(entity: SourceDto) = Source(
        entity.id.toString(),
        entity.name.toString()
    )
}