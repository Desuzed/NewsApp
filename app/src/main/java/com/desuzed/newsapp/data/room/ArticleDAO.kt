package com.desuzed.newsapp.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM article_table")
    fun getCachedArticles (): List<ArticleDTO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleDTO>)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

}