package pe.edu.upc.newsly.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sourceName: String,
    val author: String,
    val title: String,
    val urlToImage: String,
    val content: String,
    val year: Int,
    val url: String,
    val description: String
)
