package com.desuzed.newsapp.data.retrofit.dto

import com.desuzed.newsapp.model.Article
import com.google.gson.annotations.SerializedName


data class ArticleDto(
    @SerializedName("source") var source: SourceDto = SourceDto(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var publishedAt: String = "",
    @SerializedName("content") var content: String = "",

)

class ArticleMapper : EntityMapper<ArticleDto, Article> {
    override fun mapFromEntity(entity: ArticleDto): Article {
        val source = SourceMapper().mapFromEntity(entity.source)
        return Article(
            source,
            entity.author.toString(),
            entity.title.toString(),
            entity.description,
            entity.url,
            entity.urlToImage.toString(),
            entity.publishedAt,
            entity.content,
        )
    }
}
