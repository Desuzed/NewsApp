package com.desuzed.newsapp.data.retrofit.dto.mappers

interface EntityMapper <Entity, DomainModel> {
    fun mapFromEntity (entity: Entity) : DomainModel
}