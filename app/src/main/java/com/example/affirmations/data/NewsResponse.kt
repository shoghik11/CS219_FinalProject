package com.example.affirmations.data

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?,
    @SerializedName("articles")
    var articles: List<ArticleResponse>
)
class ArticleResponse(
    @SerializedName("source")
    var source: SourceResponse?,
    @SerializedName("author")
    var author: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("urlToImage")
    var urlToImage: String?,
)
class SourceResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String?,
)
