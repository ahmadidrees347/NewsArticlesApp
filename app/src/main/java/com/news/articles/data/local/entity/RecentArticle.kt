package com.news.articles.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recent_articles")
data class RecentArticle(
        @PrimaryKey
        val url: String,
        val id: Int,
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val title: String?,
        val urlToImage: String?
) : Serializable