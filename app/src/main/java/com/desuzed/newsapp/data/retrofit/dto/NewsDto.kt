package com.desuzed.newsapp.data.retrofit.dto

import com.desuzed.newsapp.model.Article
import com.desuzed.newsapp.model.News
import com.google.gson.annotations.SerializedName

class NewsDto {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("totalResults")
    var totalResults: Int = 0
    @SerializedName("articles")
    var articles: List<ArticleDto> = arrayListOf()
}

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