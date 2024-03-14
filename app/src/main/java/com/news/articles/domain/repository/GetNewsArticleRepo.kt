package com.news.articles.domain.repository

import androidx.paging.PagingData
import com.news.articles.data.local.entity.RecentArticle
import com.news.articles.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface GetNewsArticleRepo {
    suspend fun getNewsArticle(): List<Article>
    suspend fun getRecentNews(): Flow<PagingData<RecentArticle>>
}