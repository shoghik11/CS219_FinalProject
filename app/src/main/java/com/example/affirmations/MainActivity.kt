package com.example.affirmations

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.affirmations.data.ArticleResponse
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import coil.compose.AsyncImage
import com.example.affirmations.model.Filter
import androidx.compose.ui.Modifier
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

/*
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ItemListScreen(navController: NavController, items: List<String>) {
    val selectedItem = remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Item List") }) }
    ) {
        Column(Modifier.padding(16.dp)) {
            for (item in items) {
                ListItem(
                    text = { Text(item) },
                    modifier = Modifier.clickable { selectedItem.value = item }
                )
            }
        }
    }

    selectedItem.value?.let { item ->
        DetailsScreen(navController = navController, item = item) {
            selectedItem.value = null
        }
    }
}
*/


//@Composable
//fun DetailsScreen(navController: NavController, item: String, onBackClick: () -> Unit) {
//    Scaffold(
//        topBar = { TopAppBar(title = { Text(item) }, navigationIcon = {
//            IconButton(onClick = onBackClick) {
//                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//            }
//        }) }
//    ) {
//        Column(Modifier.padding(16.dp)) {
//            Text("Details for $item")
//        }
//    }
//}

