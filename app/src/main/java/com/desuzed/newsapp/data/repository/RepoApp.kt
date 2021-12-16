package com.desuzed.newsapp.data.repository

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.data.ErrorProvider
import com.desuzed.newsapp.data.retrofit.dto.mappers.ErrorMapper
import com.desuzed.newsapp.data.retrofit.dto.mappers.NewsMapper
import com.desuzed.newsapp.model.Error
import com.desuzed.newsapp.model.News

class RepoAppImpl (private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) : RepoApp {

    //TODO нуждается в рефакторинге. сильная связанность
    override suspend fun fetchNewsFromApi(query: String): Pair<News?, Error?> {
        return when (val response = remoteDataSource.fetchNews(query)) {
            is NetworkResponse.Success -> {
                val pair = Pair(NewsMapper().mapFromEntity(response.body), null)
                return if (pair.first.articles.isEmpty()) {
                    Pair(
                        null,
                        Error("Nothing found", "", localDataSource.parseCode(ErrorProvider.NO_DATA))
                    )
                } else {
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

}

interface RepoApp {
    suspend fun fetchNewsFromApi(query: String): Pair<News?, Error?>
}