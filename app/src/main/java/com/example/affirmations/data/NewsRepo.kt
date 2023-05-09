package com.example.affirmations.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.affirmations.data.ArticleResponse
import com.example.affirmations.data.SourceResponse
class NewsRepo(val apiService: NewsApiService) {
    suspend fun fetchNews(country: String): List<ArticleResponse> {
        withContext(Dispatchers.IO) {
            Thread.sleep(5000)
        }
        return apiService.fetchNews(country).run {
            this.articles.map {
                ArticleResponse(
                    SourceResponse(it.source?.id ?: "", it.source?.name ?: ""),
                    it.title ?: "", it.author ?: "", it.urlToImage
                )
            }
        }
    }
    suspend fun fetchNewsWithCategory(country: String, category: String, query: String): List<ArticleResponse> {
        return apiService.fetchNewsWithCategory(country,category,query).run {
            this.articles.map {
                ArticleResponse(
                    SourceResponse(it.source?.id ?: "", it.source?.name ?: ""),
                    it.title ?: "", it.author ?: "", it.urlToImage
                )
            }
        }
    }
}