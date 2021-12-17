package com.desuzed.newsapp.data.retrofit.dto

interface EntityMapper <Entity, DomainModel> {
    fun mapFromEntity (entity: Entity) : DomainModel
}