package com.desuzed.newsapp.data.retrofit.dto

import com.google.gson.annotations.SerializedName


data class ArticleDto(
    @SerializedName("source") var source: SourceDto = SourceDto(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("urlToImage") var urlToImage: String = "",
    @SerializedName("publishedAt") var publishedAt: String = "",
    @SerializedName("content") var content: String = "",

)
