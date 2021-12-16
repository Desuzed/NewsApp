package com.desuzed.newsapp.data.repository

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.data.NewsService
import com.desuzed.newsapp.data.retrofit.dto.ErrorDto
import com.desuzed.newsapp.data.retrofit.dto.NewsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl : RemoteDataSource {
    override suspend fun fetchNews(query: String): NetworkResponse<NewsDto, ErrorDto> =
        withContext(Dispatchers.IO) {
            NewsService
                .getInstance()
                .fetchNews(query)
        }
}

interface RemoteDataSource {
    suspend fun fetchNews(query: String): NetworkResponse<NewsDto, ErrorDto>
}