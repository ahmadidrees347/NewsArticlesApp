package com.news.articles.data.network

import com.news.articles.BuildConfig
import com.news.articles.data.model.NewsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // https://newsapi.org/v2/top-headlines?country=us&apiKey=436a7b507ee5433bafa1ad67c8eff93b
    // https://api.github.com/search/users?sort=followers&q=a&page=1&per_page=10

    @GET("everything?q=phone")
    suspend fun getNewsArticles(
        @Query("page") page: Int? = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String = BuildConfig.APIKey
    ): NewsDTO

    @GET("top-headlines")
    suspend fun getTopNewsArticles(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.APIKey
    ): NewsDTO

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") q: String,
        @Query("page") page: Int?,
        @Query("apiKey") apiKey: String = BuildConfig.APIKey
    ): Response<NewsDTO>
}