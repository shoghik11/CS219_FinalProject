package com.example.affirmations

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.affirmations.data.ArticleResponse
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsRefreshableLazyColumn(
    data: List<ArticleResponse>,
    onRefresh: () -> Unit,
    navController: NavController,
) {
    val state = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        state = state,
        onRefresh = {
            onRefresh()
            state.isRefreshing = false
        }
    ) {
      //  ArticleList(navController, data)
    }
}