package com.desuzed.newsapp.model

class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)