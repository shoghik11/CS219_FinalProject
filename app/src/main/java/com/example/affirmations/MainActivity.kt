package com.example.affirmations

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.affirmations.data.ArticleResponse
import com.example.affirmations.ui.theme.AffirmationsTheme
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import coil.compose.rememberCoilPainter





class MainActivity : ComponentActivity() {
    val viewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val postsResult = viewModel.articles.observeAsState(Result.loading()).value
                    Column {
                        Button(onClick = { viewModel.loadNews() }) {
                            Text(text = "Get News")
                        }
                        NewsList(postsResult)
                    }
                }
            }
        }
    }
}
@Composable
fun NewsList(listResult: Result<List<ArticleResponse>>) {
    when (listResult) {
        is Result.Success -> {
            val posts = listResult.data
            LazyColumn {
                items(posts) { post ->
                    Card(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            Modifier.padding(8.dp)
                        ) {
                            post.source?.let {
                                Text(
                                    text = "Source ID: ${it.id}, Name: ${it.name}",
                                    style = MaterialTheme.typography.body2
                                )
                            }
                            post.urlToImage?.let {
                                Image(
                                    painter = rememberCoilPainter(it),
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp, 80.dp)
                                )
                            }
                            post.title?.let { Text(text = it, style = MaterialTheme.typography.h5) }
                            post.author?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            Text(text = "Error: ${listResult.exception.message}")
        }
        else -> {

        }
    }
}

//@Composable
//fun NewsList(listResult: Result<List<ArticleResponse>>) {
//    when (listResult) {
//        is Result.Success -> {
//            val posts = listResult.data
//            LazyColumn {
//                items(posts) { post ->
//                    Card(
//                        Modifier
//                            .padding(8.dp)
//                            .fillMaxWidth()
//                    ) {
//                        Column(
//                            Modifier.padding(8.dp)
//                        ) {
//                            post.title?.let { Text(text = it, style = MaterialTheme.typography.h5) }
//                            //Text(text = post.title, style = MaterialTheme.typography.body1)
//                            post.
//                        }
//                    }
//                }
//            }
//        }
//        is Result.Error -> {
//            Text(text = "Error: ${listResult.exception.message}")
//        }
//        else -> {
//
//        }
//    }
//}
