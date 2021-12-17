package com.desuzed.newsapp.data.repository

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.data.ErrorProvider
import com.desuzed.newsapp.data.retrofit.dto.ErrorMapper
import com.desuzed.newsapp.data.retrofit.dto.NewsMapper
import com.desuzed.newsapp.data.room.ArticleDTO
import com.desuzed.newsapp.data.room.ArticleDTOToArticleMapper
import com.desuzed.newsapp.data.room.ArticleToDTOMapper
import com.desuzed.newsapp.model.Article
import com.desuzed.newsapp.model.Error
import com.desuzed.newsapp.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoAppImpl (private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) : RepoApp {

    override suspend fun fetchNewsFromApi(query: String): Pair<News?, Error?> =
        withContext(Dispatchers.IO)
        {
            when (val response = remoteDataSource.fetchNews(query)) {
                is NetworkResponse.Success -> {
                    val pair = Pair(NewsMapper().mapFromEntity(response.body), null)
                    if (pair.first.articles.isEmpty()) {
                        Pair(
                            null,
                            Error(
                                "Nothing found",
                                "",
                                localDataSource.parseCode(ErrorProvider.NO_DATA)
                            )
                        )
                    } else {
                        insertArticles(mapArticles(pair.first.articles))
                        pair
                    }
                }
                is NetworkResponse.ApiError -> {
                    val pair = Pair(null, ErrorMapper().mapFromEntity(response.body))
                    pair.second.message = localDataSource.parseCode(pair.second.code)
                    pair
                }
                is NetworkResponse.NetworkError -> {
                    val pair = Pair(null, Error("Network Error", "", ""))
                    pair.second.message = localDataSource.parseCode(ErrorProvider.NO_INTERNET)
                    pair
                }
                is NetworkResponse.UnknownError -> {
                    val pair = Pair(null, Error("Unknown Error", "", ""))
                    pair.second.message = localDataSource.parseCode(ErrorProvider.UNKNOWN)
                    pair
                }
            }
        }

    override suspend fun loadCachedNews(): News = withContext(Dispatchers.IO) {
        val resultList = mutableListOf<Article>()
        getCachedArticles().forEach {
            resultList.add(ArticleDTOToArticleMapper().mapFromEntity(it))
        }
        News("Cached", resultList.size, resultList)
    }

    private fun mapArticles(articles: List<Article>): List<ArticleDTO> {
        val resultList = mutableListOf<ArticleDTO>()
        articles.forEach {
            resultList.add(ArticleToDTOMapper().mapFromEntity(it))
        }
        return resultList
    }

    override fun insertArticles(list: List<ArticleDTO>) {
        deleteAllArticles()
        localDataSource.insertArticles(list)
    }

    override fun deleteAllArticles() {
        localDataSource.deleteAllArticles()
    }

    override suspend fun getCachedArticles(): List<ArticleDTO> =
        localDataSource.getCachedArticles()

    override fun clearJob() {
        localDataSource.clearJob()
    }

}

interface RepoApp : RoomProvider {
    suspend fun fetchNewsFromApi(query: String): Pair<News?, Error?>
    suspend fun loadCachedNews(): News
    fun clearJob()
}