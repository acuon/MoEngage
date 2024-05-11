package com.acuon.moengage.domain.model


import androidx.lifecycle.ViewModel
import com.acuon.moengage.common.Constants
import com.acuon.moengage.utils.extensions.serverToPrettyDate
import com.google.gson.annotations.SerializedName

data class NewsResponseDTO(
    @SerializedName("articles")
    val articles: List<Article?>? = null,
    @SerializedName("status")
    val status: String? = null
)

data class Source(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class Article(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    @SerializedName("source")
    val source: Source? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("urlToImage")
    val urlToImage: String? = null
) : ViewModel() {

    val prettyDate: String
        get() = publishedAt.serverToPrettyDate(
            Constants.DateFormats.SERVER_FORMAT,
            Constants.DateFormats.APP_DATE_FORMAT
        )

}