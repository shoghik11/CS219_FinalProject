package com.example.randomusergenerator.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.randomusergenerator.data.model.Filter
import com.example.randomusergenerator.data.model.User
import com.example.randomusergenerator.ui.components.FilterButton
import com.example.randomusergenerator.ui.components.SearchBar
import com.example.randomusergenerator.ui.components.UsersRefreshableLazyColumn

@Composable
fun UsersScreen(navController:  NavHostController,
                users: List<User>,
                onRefresh: () -> Unit,
                onSelectFilter: (String) -> Unit) {
    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            FilterButton(
                filters = listOf(
                    Filter(1, "Female"),
                    Filter(2, "Male"),
                ),
                onFilterSelected = { filter ->
                    onSelectFilter(filter.name)  // API Request with filter
                }
            )

            SearchBar(onSearch = { query ->
                //onSearch(query)
                Log.d(">>>", query)
            })
        }

        UsersRefreshableLazyColumn(
            data = users,
            onRefresh = onRefresh,
            navController = navController
        )
    }
}