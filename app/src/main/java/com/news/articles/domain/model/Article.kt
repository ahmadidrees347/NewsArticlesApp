package com.news.articles.domain.model

data class Article(
    val content: String,
    val description: String,
    val title: String,
    val urlToImage: String,
    val url: String?,
)
