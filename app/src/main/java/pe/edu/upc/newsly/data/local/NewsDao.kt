package pe.edu.upc.newsly.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert
    suspend fun insertNews(news: NewsEntity)

    @Delete
    suspend fun deleteNews(news: NewsEntity)

    @Query("SELECT * FROM favorite_news")
    suspend fun getAllNews(): List<NewsEntity>

    @Query("SELECT * FROM favorite_news WHERE url = :url")
    suspend fun getNewsByUrl(url: String): NewsEntity?
}