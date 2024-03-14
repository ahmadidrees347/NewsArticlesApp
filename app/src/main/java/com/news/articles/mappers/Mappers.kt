package com.news.articles.mappers

import com.news.articles.data.local.entity.PopularArticle
import com.news.articles.data.local.entity.RecentArticle
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

fun List<ArticleDTO>.toDomainRecentArticle(): List<RecentArticle> {
    return map {
        RecentArticle(
            id = 0,
            url = it.url ?: "",
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            title = it.title,
            urlToImage = it.urlToImage
        )
    }
}

fun List<ArticleDTO>.toDomainPopularArticle(): List<PopularArticle> {
    return map {
        PopularArticle(
            id = null,
            url = it.url ?: "",
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            title = it.title,
            urlToImage = it.urlToImage
        )
    }
}

fun List<RecentArticle>.toRecentArticle(): List<Article> {
    return map {
        Article(
            url = it.url,
            content = it.content ?: "",
            description = it.description ?: "",
            title = it.title ?: "",
            urlToImage = it.urlToImage ?: ""
        )
    }
}

fun RecentArticle.toArticle(): Article {
    return Article(
        content = content ?: "",
        description = description ?: "",
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: ""
    )
}

fun ArticleDTO.toRecentArticle(counter:Int): RecentArticle {
    return RecentArticle(
        id = counter,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url ?: "",
        urlToImage = urlToImage ?: ""
    )
}