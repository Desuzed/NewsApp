package com.desuzed.newsapp.data.retrofit.dto

import com.google.gson.annotations.SerializedName

class NewsDto {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("totalResults")
    var totalResults: Int = 0
    @SerializedName("articles")
    var articles: List<ArticleDto> = arrayListOf()
}