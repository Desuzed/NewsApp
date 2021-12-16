package com.desuzed.newsapp.data.repository

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.newsapp.model.Error
import com.desuzed.newsapp.model.News

interface DataSource {
    suspend fun getNews(query : String) : NetworkResponse <News, Error>
}