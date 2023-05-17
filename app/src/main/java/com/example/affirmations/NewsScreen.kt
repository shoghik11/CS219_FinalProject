package com.example.affirmations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.affirmations.data.ArticleResponse
import com.example.affirmations.model.Filter

@Composable
fun NewsScreen(navController:  NavHostController,
            //    onRefresh: () -> Unit,
                viewModel: DataLoaderViewModel
               ) {
    val articleList = viewModel.articles.observeAsState(Result.loading()).value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(color = Color(0xFF6A1B9A))
            ) {
                Column() {
                    Text(
                        text = "NEWS ",
                        style = MaterialTheme.typography.h2,
                        color = Color.White,
                        modifier = Modifier.padding(15.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                    ) {
                        SearchBar(
                            onSearch = { searchText ->
                                viewModel.getPostsWithCategory("", searchText)
                            }, modifier = Modifier
                                .weight(1f)
                                .background(Color.White),
                            filters = listOf(
                                Filter(1, "Business"),
                                Filter(2, "Entertainment"),
                                Filter(3, "General"),
                                Filter(4, "Health"),
                            ),
                            onFilterSelected = { filter ->
                                viewModel.getPostsWithCategory(filter.name.lowercase(),"")
                            }
                        )
                    }
                }
            }
            ArticleList(articleList,navController)
        }
    }
}

//    NewsRefreshableLazyColumn(
//        data = news,
//        onRefresh = onRefresh,
//        navController = navController
//    )
//}

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    filters: List<Filter>,
    onFilterSelected: (Filter) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = { query = it },
        modifier = modifier,
        placeholder = { Text("Search") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(query) }
        ),
        leadingIcon = {
            IconButton(
                onClick = { onSearch(query) },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    Icons.Default.List,
                    contentDescription = "Filter icon"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filters.forEach { filter ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onFilterSelected(filter)
                        }
                    ) {
                        Text(
                            text = filter.name,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ArticleList(articleResult: Result<List<ArticleResponse>>, navController: NavHostController) {
    when (articleResult) {
        is Result.Success -> {
            val articles = articleResult.data
            LazyColumn {
                itemsIndexed(articles) { index,article ->
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(140.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Color(0xFFcacbcc)
                                )
                                .clickable{navController.navigate(Screens.NewsDetailsScreen.route+"/$index")}
                            ,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(140.dp)
                                    .background(Color.Red)
                            ) {
                                AsyncImage(
                                    model = article.urlToImage,
                                    contentDescription = article.title,
                                    placeholder = painterResource(id = R.drawable.image6),
                                    modifier = Modifier.fillMaxHeight(),
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize()
                                ,
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = article.source?.name ?: "",
                                    style = MaterialTheme.typography.subtitle1,
                                )
                                Text(
                                    text = article?.title ?: "",
                                    style = MaterialTheme.typography.body1,

                                    )
                                Text(
                                    text = article?.author ?: "",
                                    style = MaterialTheme.typography.subtitle2,
                                )
                            }
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    text = "No results found"
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Results Found",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(80.dp),
                    color = Color(0xFF011b45)
                )
            }
        }
    }
}