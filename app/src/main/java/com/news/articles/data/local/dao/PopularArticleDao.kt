package com.news.articles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.articles.data.local.entity.PopularArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePopularArticles(popularArticle: List<PopularArticle>)

    @Query("SELECT * FROM popular_articles")
    fun getAllPopularArticles(): Flow<List<PopularArticle>>

    @Query("DELETE FROM popular_articles")
    suspend fun deletePopularArticles()

}