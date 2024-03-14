package com.news.articles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.articles.data.local.dao.PopularArticleDao
import com.news.articles.data.local.dao.RecentArticleDao
import com.news.articles.data.local.entity.PopularArticle
import com.news.articles.data.local.entity.RecentArticle

@Database(
    entities = [RecentArticle::class, PopularArticle::class],
    version = 1
)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract val recentArticleDao: RecentArticleDao
    abstract fun popularArticleDao(): PopularArticleDao
}