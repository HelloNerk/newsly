package pe.edu.upc.newsly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import pe.edu.upc.newsly.common.Constants
import pe.edu.upc.newsly.data.local.AppDatabase
import pe.edu.upc.newsly.data.remote.NewsService
import pe.edu.upc.newsly.data.repository.NewsRepository
import pe.edu.upc.newsly.presentation.FindNewsScreen
import pe.edu.upc.newsly.presentation.FindNewsViewModel
import pe.edu.upc.newsly.presentation.navigation.Navigation
import pe.edu.upc.newsly.ui.theme.NewslyTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(NewsService::class.java)

        val dao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "news-db")
            .build()
            .getNewsDao()

        val repository = NewsRepository(service, dao)

        val viewModel = FindNewsViewModel(repository)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewslyTheme {
                Navigation(viewModel)
            }
        }
    }
}
