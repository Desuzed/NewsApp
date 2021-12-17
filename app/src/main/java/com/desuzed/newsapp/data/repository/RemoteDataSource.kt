package com.desuzed.newsapp.data.repository

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.data.NewsService
import com.desuzed.newsapp.data.retrofit.dto.ErrorDto
import com.desuzed.newsapp.data.retrofit.dto.NewsDto

class RemoteDataSourceImpl : RemoteDataSource {
    override suspend fun fetchNews(query: String): NetworkResponse<NewsDto, ErrorDto> =
        NewsService
                .getInstance()
                .fetchNews(query)
}

interface RemoteDataSource {
    suspend fun fetchNews(query: String): NetworkResponse<NewsDto, ErrorDto>
}