package pe.edu.upc.newsly.domain

data class News(
    val sourceName: String,
    val author: String,
    val title: String,
    val urlToImage: String,
    val content: String,
    val year: Int,
    val url: String,
    val description: String,
    var isFavorite: Boolean = false
)
