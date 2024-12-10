package pe.edu.upc.newsly.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.newsly.common.Constants
import pe.edu.upc.newsly.common.Resource
import pe.edu.upc.newsly.data.local.NewsDao
import pe.edu.upc.newsly.data.local.NewsEntity
import pe.edu.upc.newsly.data.remote.NewsService
import pe.edu.upc.newsly.data.remote.toNews
import pe.edu.upc.newsly.domain.News

class NewsRepository(private val newsService: NewsService, private val newsDao: NewsDao) {

    private suspend fun isFavorite(url: String): Boolean = withContext(Dispatchers.IO) {
        newsDao.getNewsByUrl(url)?.let {
            return@withContext true
        }
        return@withContext false
    }

    suspend fun getFavoriteNews(): Resource<List<News>> = withContext(Dispatchers.IO) {
        try{
            val news = newsDao.getAllNews().map { newsEntity ->
                News(newsEntity.sourceName, newsEntity.author, newsEntity.title, newsEntity.urlToImage, newsEntity.content, newsEntity.year, newsEntity.url, newsEntity.description, true)
            }
            if (news.isNotEmpty()){
                return@withContext Resource.Success(data = news)
            }
            return@withContext Resource.Error(message = "No favorite packages found.")
        }catch (e: Exception){
            return@withContext Resource.Error(message = e.message ?: "An exception occurred.")
        }
    }

    suspend fun getNews(description: String): Resource<List<News>> = withContext(Dispatchers.IO) {
        try {
            val response = newsService.getNews(description)
            if (response.isSuccessful) {
                response.body()?.let { newsDto ->
                    val news = mutableListOf<News>()
                    newsDto.forEach { newsDto ->
                        val new = newsDto.toNews()
                        new.isFavorite = isFavorite(new.url)
                        news.add(new)
                    }
                    return@withContext Resource.Success(data = news)
                }
            }
            return@withContext Resource.Error(message = response.message())
        }catch (e: Exception){
            return@withContext Resource.Error(message = e.message ?: "An exception occurred.")
        }
    }

    suspend fun insert(news: News) = withContext(Dispatchers.IO) {
        newsDao.insertNews(NewsEntity(0,news.sourceName, news.author, news.title, news.urlToImage, news.content, news.year, news.url, news.description))
    }

    suspend fun delete(news: News) = withContext(Dispatchers.IO) {
        newsDao.deleteNews(NewsEntity(0,news.sourceName, news.author, news.title, news.urlToImage, news.content, news.year, news.url, news.description))
    }

}