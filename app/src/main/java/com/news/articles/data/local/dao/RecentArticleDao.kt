package com.news.articles.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.news.articles.data.local.entity.RecentArticle

@Dao
interface RecentArticleDao {

    @Upsert
    suspend fun upsertAll(articles: List<RecentArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecentArticles(recentArticles: List<RecentArticle>)

    @Query("SELECT * FROM recent_articles")
    fun getAllRecentArticles(): PagingSource<Int, RecentArticle>

    @Query("DELETE FROM recent_articles")
    suspend fun deleteRecentArticles()

}