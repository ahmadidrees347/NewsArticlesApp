package com.news.articles.data.repository

import com.news.articles.domain.model.Article
import com.news.articles.domain.repository.GetNewsArticleRepo
import com.news.articles.mappers.toDomain
import com.news.articles.data.network.ApiService
import com.news.articles.utils.SafeApiRequest
import javax.inject.Inject

class GetNewsArticleRepoImpl @Inject constructor(private val apiService: ApiService) :
    GetNewsArticleRepo, SafeApiRequest() {
    override suspend fun getNewsArticle(): List<Article> {
        val response = safeApiRequest { apiService.getNewsArticles() }
        return response.articles?.toDomain() ?: arrayListOf()
    }
}