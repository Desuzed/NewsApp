package com.desuzed.newsapp.data.repository

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.data.NewsService
import com.desuzed.newsapp.data.retrofit.dto.mappers.NewsMapper
import com.desuzed.newsapp.model.Error
import com.desuzed.newsapp.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class RemoteDataSourceImpl() : RemoteDataSource {
    override suspend fun getNews(query: String): NetworkResponse<News, Error> =
        withContext(Dispatchers.IO) {
            when (val response = NewsService
                .getInstance()
                .fetchNews(query)) {
                is NetworkResponse.Success -> {
                    NetworkResponse.Success(NewsMapper().mapFromEntity(response.body))
                }
                //TODO Обработать все случаи
                else -> {
                    NetworkResponse.UnknownError(Exception())
                }
            }
        }

}

interface RemoteDataSource : DataSource {
}