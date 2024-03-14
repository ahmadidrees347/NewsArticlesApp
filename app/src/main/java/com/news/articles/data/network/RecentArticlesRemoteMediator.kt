package com.news.articles.data.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.news.articles.data.local.ArticlesDatabase
import com.news.articles.data.local.entity.RecentArticle
import com.news.articles.mappers.toRecentArticle
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RecentArticlesRemoteMediator(
    private val apiDataSource: ApiService,
    private val newsArticleDb: ArticlesDatabase,
) : RemoteMediator<Int, RecentArticle>() {
    private var counter = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecentArticle>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        Log.e("pg", "${lastItem.id}")
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val articles = apiDataSource.getNewsArticles(
                page = loadKey,
                pageSize = state.config.pageSize
            )


            newsArticleDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsArticleDb.recentArticleDao.deleteRecentArticles()
                }
                val articleEntities = articles.articles?.map {
                    counter++
                    it.toRecentArticle(counter)
                } ?: arrayListOf()
                Log.e("pg", "$loadKey - ${state.config.pageSize} - ${articleEntities.size}")
                newsArticleDb.recentArticleDao.upsertAll(articleEntities)
            }
            Log.e("pg", "${articles.articles?.isEmpty() ?: false}")

            MediatorResult.Success(
                endOfPaginationReached = articles.articles?.isEmpty() ?: false
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}