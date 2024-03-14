package com.news.articles.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.articles.presentation.screens.HomeScreen
import com.news.articles.presentation.screens.MyTopAppBar
import com.news.articles.presentation.screens.RecentScreen
import com.news.articles.presentation.viewmodel.RecentNewsViewModel
import com.news.articles.ui.theme.NewsArticlesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsArticlesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = { MyTopAppBar() }) {
                        Column(
                            modifier = Modifier
                                .padding(top = it.calculateTopPadding())
                                .fillMaxSize()
                        ) {
                            val viewModel = hiltViewModel<RecentNewsViewModel>()
                            val recentNews = viewModel.recentNewsPagingFlow.collectAsLazyPagingItems()
                            RecentScreen(recentNews = recentNews)
//                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}