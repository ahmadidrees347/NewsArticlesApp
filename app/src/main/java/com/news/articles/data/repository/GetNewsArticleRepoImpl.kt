package com.news.articles.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.news.articles.data.local.ArticlesDatabase
import com.news.articles.data.local.entity.RecentArticle
import com.news.articles.data.network.ApiService
import com.news.articles.data.network.RecentArticlesRemoteMediator
import com.news.articles.domain.model.Article
import com.news.articles.domain.repository.GetNewsArticleRepo
import com.news.articles.mappers.toDomain
import com.news.articles.mappers.toDomainPopularArticle
import com.news.articles.utils.Resource
import com.news.articles.utils.SafeApiRequest
import com.news.articles.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetNewsArticleRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val articlesDatabase: ArticlesDatabase
) :
    GetNewsArticleRepo, SafeApiRequest() {

    private val popularNewsArticleDao = articlesDatabase.popularArticleDao()

    override suspend fun getNewsArticle(): List<Article> {
        return apiService.getNewsArticles().articles?.toDomain() ?: arrayListOf()
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getRecentNews(): Flow<PagingData<RecentArticle>> =
        Pager(
            PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            remoteMediator = RecentArticlesRemoteMediator(
                apiService,
                articlesDatabase
            ),
            pagingSourceFactory = {
                articlesDatabase.recentArticleDao.getAllRecentArticles()
            }
        ).flow

    fun getPopularNews(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<out Any?>> =
        networkBoundResource(
            query = {
                popularNewsArticleDao.getAllPopularArticles()
            },
            fetch = {
                val response = apiService.getTopNewsArticles()
                response.articles
            },
            saveFetchResult = { popularNews ->
                popularNews?.let {
                    val popularNewsArticles = popularNews.map { it }
                    articlesDatabase.withTransaction {
                        popularNewsArticleDao.deletePopularArticles()
                        popularNewsArticleDao.savePopularArticles(popularNewsArticles.toDomainPopularArticle())
                    }
                }
            },
            shouldFetch = { cachedPopularArticles ->
                if (forceRefresh) {
                    true
                } else {
                    val sortedArticles = cachedPopularArticles.sortedBy { article ->
                        article.updatedAt
                    }
                    val oldestTimeStamp = sortedArticles.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimeStamp == null ||
                            oldestTimeStamp < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(
                        60
                    )
                    needsRefresh
                }
            },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = { t ->
                if (t !is HttpException && t !is IOException) {
                    throw t
                }
                onFetchFailed(t)
            }
        )
}