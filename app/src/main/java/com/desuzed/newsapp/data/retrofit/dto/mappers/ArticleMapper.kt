package com.desuzed.newsapp.data.retrofit.dto.mappers

import com.desuzed.newsapp.data.retrofit.dto.ArticleDto
import com.desuzed.newsapp.model.Article

class ArticleMapper : EntityMapper<ArticleDto, Article> {
    override fun mapFromEntity(entity: ArticleDto): Article {
        val source = SourceMapper().mapFromEntity(entity.source)
        return Article(
            source,
            entity.author.toString(),
            entity.title.toString(),
            entity.description,
            entity.url,
            entity.urlToImage,
            entity.publishedAt,
            entity.content,
        )
    }
}