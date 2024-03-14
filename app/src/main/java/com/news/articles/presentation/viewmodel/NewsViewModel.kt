package com.news.articles.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.news.articles.data.local.entity.RecentArticle
import com.news.articles.data.network.RecentArticlesRemoteMediator
import com.news.articles.data.repository.GetNewsArticleRepoImpl
import com.news.articles.domain.use_case.GetNewsArticleUseCase
import com.news.articles.presentation.HomeStateHolder
import com.news.articles.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor
    (private val getNewsArticleUseCase: GetNewsArticleUseCase,
     private val allNewsRepository: GetNewsArticleRepoImpl) : ViewModel() {

    val articles = mutableStateOf(HomeStateHolder())

    init {
        getNewsArticles()
    }



    suspend fun getRecentNews() : Flow<PagingData<RecentArticle>> {


        return allNewsRepository.getRecentNews()
            .cachedIn(viewModelScope)
//        return allNewsRepository.getRecentNews().onEach {
//            when (it) {
//                is Resource.Loading -> {
//                    articles.value = HomeStateHolder(isLoading = true)
//                }
//                is Resource.Success -> {
//                    articles.value = HomeStateHolder(data = it.data)
//                }
//                is Resource.Error -> {
//                    articles.value = HomeStateHolder(error = it.message.toString())
//                }
//            }
//        }.cachedIn(viewModelScope)
    }

    private fun getNewsArticles() {
        getNewsArticleUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    articles.value = HomeStateHolder(isLoading = true)
                }
                is Resource.Success -> {
                    articles.value = HomeStateHolder(data = it.data)
                }
                is Resource.Error -> {
                    articles.value = HomeStateHolder(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}