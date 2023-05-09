package com.example.affirmations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.affirmations.data.*
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {

    private val _articles = MutableLiveData<Result<List<ArticleResponse>>>()
    val articles: LiveData<Result<List<ArticleResponse>>> = _articles

    fun getPosts() {
        viewModelScope.launch {
            _articles.postValue(Result.loading())
            try {
                val response = NewsRepo(
                    RetrofitHelper.getInstance().create(NewsApiService::class.java)
                ).fetchNews("us")
                _articles.postValue(Result.success(response))
            } catch (e: Exception) {
                e.message?.let { Log.e("Error loading ViewModel", it) }
                _articles.postValue(Result.error(e))
            }
        }
    }
    fun getPostsWithCategory(category:String,query: String){
        viewModelScope.launch {
            try {
                val response = NewsRepo(
                    RetrofitHelper.getInstance().create(NewsApiService::class.java)
                ).fetchNewsWithCategory("us",category,query)
                if(response.isEmpty()){
                    _articles.postValue(Result.error(RuntimeException("No data")))
                } else {
                    _articles.postValue(Result.success(response))
                }
            } catch (e: Exception) {
                Log.d(">>>", e.message.toString())
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