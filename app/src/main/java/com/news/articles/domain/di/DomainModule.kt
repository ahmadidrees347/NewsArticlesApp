package com.news.articles.domain.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.news.articles.data.local.ArticlesDatabase
import com.news.articles.data.local.entity.RecentArticle
import com.news.articles.data.network.ApiService
import com.news.articles.data.network.RecentArticlesRemoteMediator
import com.news.articles.data.repository.GetNewsArticleRepoImpl
import com.news.articles.domain.repository.GetNewsArticleRepo
import com.news.articles.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application)
            = Room.databaseBuilder(application, ArticlesDatabase::class.java, "article_database")
        .fallbackToDestructiveMigration()
//        .addCallback(callback)
        .build()

    @Provides
    fun provideGetNewsRepo(apiService: ApiService, articlesDatabase: ArticlesDatabase): GetNewsArticleRepo {
        return GetNewsArticleRepoImpl(apiService = apiService, articlesDatabase)
    }

    @Provides
    @Singleton
    fun provideArticlePager(articleDb: ArticlesDatabase, apiService: ApiService): Pager<Int, RecentArticle> {
        return Pager(
            config = PagingConfig(pageSize = Constant.QUERY_PAGE_SIZE),
            remoteMediator = RecentArticlesRemoteMediator(
                apiService,
                articleDb
            ),
            pagingSourceFactory = {
                articleDb.recentArticleDao.getAllRecentArticles()
            }
        )
    }
}