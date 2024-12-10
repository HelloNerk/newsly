package pe.edu.upc.newsly.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("articles.php")
    suspend fun getNews(@Query("description") description: String): Response<List<NewsDto>>
}