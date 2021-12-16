package com.desuzed.newsapp.data.retrofit.dto.mappers

import com.desuzed.newsapp.data.retrofit.dto.SourceDto
import com.desuzed.newsapp.model.Source

class SourceMapper : EntityMapper<SourceDto, Source> {
    override fun mapFromEntity(entity: SourceDto) = Source(
        entity.id.toString(),
        entity.name.toString()
    )
}