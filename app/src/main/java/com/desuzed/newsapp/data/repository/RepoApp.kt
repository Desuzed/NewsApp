package com.desuzed.newsapp.data.repository

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.model.Error
import com.desuzed.newsapp.model.News

class RepoAppImpl (private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) : RepoApp{
    override suspend fun getNews(query: String): NetworkResponse<News, Error> {
        return remoteDataSource.getNews(query)
    }

}

interface RepoApp : DataSource {
}