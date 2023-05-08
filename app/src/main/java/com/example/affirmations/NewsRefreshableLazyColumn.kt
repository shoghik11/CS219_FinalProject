package com.example.randomusergenerator.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.randomusergenerator.data.model.User
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun UsersRefreshableLazyColumn(
    data: List<User>,
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
        UsersList(navController, data)
    }
}