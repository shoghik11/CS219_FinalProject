package com.example.affirmations.data


import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("/v2/top-headlines?ApiKey=ab3958a774d3493494a5925e50e969c6")
    suspend fun fetchNews(@Query("country") country: String): NewsResponse
}
