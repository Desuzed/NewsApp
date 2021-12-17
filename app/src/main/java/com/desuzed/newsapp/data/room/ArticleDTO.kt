package com.desuzed.newsapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.desuzed.newsapp.data.retrofit.dto.EntityMapper
import com.desuzed.newsapp.model.Article
import com.desuzed.newsapp.model.Source

@Entity(tableName = "article_table")
class ArticleDTO(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
    @ColumnInfo(name = "source_id") val sourceId: String,
    @ColumnInfo(name = "source_name") val sourceName: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "content") val content: String
)

class ArticleToDTOMapper : EntityMapper<Article, ArticleDTO> {
    override fun mapFromEntity(entity: Article): ArticleDTO =
         ArticleDTO(
            entity.title,
            entity.description,
            entity.urlToImage,
            entity.source.id,
            entity.source.name,
            entity.author,
            entity.url,
            entity.publishedAt,
            entity.content
        )
}

class ArticleDTOToArticleMapper : EntityMapper<ArticleDTO, Article> {
    override fun mapFromEntity(entity: ArticleDTO): Article =
        Article(
            Source(entity.sourceId, entity.sourceName),
            entity.author,
            entity.title,
            entity.description,
            entity.url,
            entity.urlToImage,
            entity.publishedAt,
            entity.content
        )
}
