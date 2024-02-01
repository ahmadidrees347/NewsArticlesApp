package com.news.articles.domain.repository

import com.news.articles.domain.model.Article

interface GetNewsArticleRepo {
    suspend fun getNewsArticle(): List<Article>
}