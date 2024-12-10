package pe.edu.upc.newsly.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.newsly.common.Constants
import pe.edu.upc.newsly.domain.News

data class NewsDto(
    @SerializedName("source")
    val source: SourceDto,

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("publishedAt")
    val publishedAt: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("description")
    val description: String
)

data class SourceDto(
    @SerializedName("name")
    val name: String
)

fun NewsDto.toNews(): News = News(
    sourceName = source.name,
    author = author ?: "",
    title = title,
    urlToImage = urlToImage?: Constants.DEFAULT_IMAGE_URL,
    content = content ?: "",
    year = publishedAt.substring(0, 4).toInt(),
    url = url,
    description = ""
)

