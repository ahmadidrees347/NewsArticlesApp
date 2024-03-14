package com.news.articles.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.news.articles.data.local.entity.RecentArticle
import com.news.articles.mappers.toArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class RecentNewsViewModel @Inject constructor(
    pager: Pager<Int, RecentArticle>
) : ViewModel() {

    val recentNewsPagingFlow =
        pager.flow
            .map { pagingData ->
                pagingData.map { it.toArticle() }
            }
            .cachedIn(viewModelScope)
}
