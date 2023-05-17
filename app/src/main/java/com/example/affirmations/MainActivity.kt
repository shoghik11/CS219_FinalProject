package com.example.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<DataLoaderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPosts()
        viewModel.articles.observe(this) { articleList ->
            setContent {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screens.NewsScreen.route
                ) {

                    composable(route = Screens.NewsScreen.route) {
                        NewsScreen(navController, viewModel)
                    }
                    composable(
                        route = Screens.NewsDetailsScreen.route + "/{index}",
                        arguments = listOf(navArgument(name = "index") {
                            type = NavType.IntType
                        })
                    ) { entry ->
                        val index = entry.arguments?.getInt("index")

                        index?.let {
                            if (articleList is Result.Success) {
                                articleList.data[index]
                            } else {
                                null
                            }
                        }?.let {
                            NewsDetailsScreen(navController, it)
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun Progress() {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        CircularProgressIndicator()
//    }
//}
