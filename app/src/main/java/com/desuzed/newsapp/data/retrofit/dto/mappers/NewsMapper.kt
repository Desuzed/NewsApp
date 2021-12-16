package com.desuzed.newsapp.data.retrofit.dto.mappers

import com.desuzed.newsapp.data.retrofit.dto.NewsDto
import com.desuzed.newsapp.model.Article
import com.desuzed.newsapp.model.News

class NewsMapper : EntityMapper<NewsDto, News> {
    override fun mapFromEntity(entity: NewsDto): News {
        val articles = mutableListOf<Article>()
        entity.articles.forEach {
            articles.add(ArticleMapper().mapFromEntity(it))
        }
        return News(
            entity.status,
            entity.totalResults,
            articles
        )
    }
}