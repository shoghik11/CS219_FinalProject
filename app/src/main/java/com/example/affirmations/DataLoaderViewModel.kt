package com.example.affirmations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.affirmations.data.NewsRepo
import com.example.affirmations.data.ArticleResponse
import com.example.affirmations.data.NewsApiService
import com.example.affirmations.data.RetrofitHelper
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {

    private val _articleList = MutableLiveData<Result<List<ArticleResponse>>>()
    val articles: LiveData<Result<List<ArticleResponse>>> =  _articleList

    fun loadNews() {
        viewModelScope.launch {
            try {
                val response = NewsRepo(RetrofitHelper.getInstance()
                    .create(NewsApiService::class.java))
                    .loadNews()?: emptyList()
                _articleList.postValue(Result.success(response))

            } catch (e: Exception) {
                _articleList.postValue(Result.error(e))
            }
        }
    }
}

sealed class Result<out T : Any> {
    data class Loading(val message: String = "") : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T : Any> loading(message: String = ""): Result<T> = Loading(message)
        fun <T : Any> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
    }
}