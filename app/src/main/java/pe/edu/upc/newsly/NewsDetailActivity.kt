package pe.edu.upc.newsly

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.newsly.presentation.FindNewsViewModel

class NewsDetailActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsTitle = intent.getStringExtra("newsTitle")
        val newsSource = intent.getStringExtra("newsSource")
        val newsImageUrl = intent.getStringExtra("newsImageUrl")
        val newsContent = intent.getStringExtra("newsContent")
        val newsUrl = intent.getStringExtra("newsUrl")

        setContent {
            NewsDetailScreen(
                title = newsTitle ?: "Sin título",
                source = newsSource ?: "Fuente desconocida",
                imageUrl = newsImageUrl,
                content = newsContent ?: "Sin contenido disponible",
                url = newsUrl ?: "https://www.google.com",
                context = this
            )
        }
    }
}

@Composable
fun NewsDetailScreen(title: String, source: String, imageUrl: String?, content: String, url:String, context: ComponentActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Publicado por: $source",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        imageUrl?.let {
            GlideImage(
                modifier = Modifier
                    .height(144.dp)
                    .clip(RoundedCornerShape(8.dp)),
                imageModel = { it },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(browserIntent)
                }
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Open in browser")
                Spacer(Modifier.width(8.dp))
                Text("Go to Website")
            }

            Button(
                onClick = {
                    // TODO: Implement saving to favorites using Room
                }
            ) {
                Icon(Icons.Default.Favorite, contentDescription = "Add to favorites")
                Spacer(Modifier.width(8.dp))
                Text("Add to Favorites")
            }
        }
    }

}
