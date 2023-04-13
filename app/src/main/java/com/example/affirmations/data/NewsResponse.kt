package com.example.affirmations.data

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?,
    @SerializedName("articles")
    var articles: List<ArticleResponse>?
)
class ArticleResponse(
    @SerializedName("source")
    var source: SourceResponse?,
    @SerializedName("author")
    var author: String?,
    @SerializedName("title")
    var title: String?,
//    @SerializedName("description")
//    var description: String?,
    @SerializedName("urlToImage")
    var urlToImage: String?,
)
class SourceResponse(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
)
/*
class ArticleResponse1(
    @SerializedName("source")
    var source: SourceResponse?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
)
class SourceResponse1(
    @SerializedName("author")
    var author: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("description")
    var description: String?,
//    @SerializedName("url")
//    var url: String?,
    @SerializedName("urlToImage")
    var urlToImage: String?,
//    @SerializedName("publishedAt")
//    var publishedAt: String?,
//    @SerializedName("content")
//    var content: String?,
)

*/