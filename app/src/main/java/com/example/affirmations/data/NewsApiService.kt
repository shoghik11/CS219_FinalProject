package com.example.affirmations.data

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("top-headlines/")
    suspend fun fetchNews(@Query("country") country: String,
                          @Query("apiKey") apiKey: String = "7312849698924abd8fb1cf559e1a6403"
    ): NewsResponse

    @GET("top-headlines/")
    suspend fun fetchNewsWithCategory(@Query("country") country: String,
                                      @Query("category") category: String? = null,
                                      @Query("q") query: String? = null,
                                      @Query("apiKey") apiKey: String = "7312849698924abd8fb1cf559e1a6403"
    ): NewsResponse
}

