package com.news.articles.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "popular_articles")
data class PopularArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val url: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val updatedAt: Long = System.currentTimeMillis(),
    val urlToImage: String?
) : Serializable