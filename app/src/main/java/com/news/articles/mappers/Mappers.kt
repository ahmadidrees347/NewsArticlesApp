package com.news.articles.mappers

import com.news.articles.data.model.ArticleDTO
import com.news.articles.domain.model.Article

fun List<ArticleDTO>.toDomain(): List<Article> {
    return map {
        Article(
            content = it.content ?: "",
            description = it.description ?: "",
            title = it.title ?: "",
            urlToImage = it.urlToImage ?: "",
            url = it.url ?: ""
        )
    }
}