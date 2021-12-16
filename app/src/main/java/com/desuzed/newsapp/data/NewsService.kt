package com.desuzed.newsapp.data

import com.desuzed.everyweather.data.network.retrofit.NetworkResponse
import com.desuzed.everyweather.data.network.retrofit.NetworkResponseAdapterFactory
import com.desuzed.newsapp.data.retrofit.dto.ErrorDto
import com.desuzed.newsapp.data.retrofit.dto.NewsDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
//https://newsapi.org/v2/top-headlines/sources?apiKey=API_KEY
interface NewsService {
    //todo ключ не добавляется в билд конфиг, разобраться с этим и персоздать ключ
    @GET("everything?apiKey=5eb0bf747eba482791fed6e9a5d78e22")
    suspend fun fetchNews(
        @Query("q") query: String
    ): NetworkResponse<NewsDto, ErrorDto>

    companion object{
        private const val baseUrl = "https://newsapi.org/v2/"
        private var weatherApiService : NewsService? = null
        fun getInstance() : NewsService {
            if (weatherApiService == null){
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(NetworkResponseAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                weatherApiService = retrofit.create(NewsService::class.java)
            }
            return weatherApiService!!
        }
    }
}