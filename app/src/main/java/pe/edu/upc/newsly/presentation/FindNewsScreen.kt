package pe.edu.upc.newsly.presentation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.newsly.NewsDetailActivity
import pe.edu.upc.newsly.domain.News
import android.content.Context
import androidx.compose.ui.platform.LocalContext

@Composable
fun FindNewsScreen(viewModel: FindNewsViewModel) {
    val state = viewModel.state.value
    val search = viewModel.search.value
    val context = LocalContext.current
    Scaffold {
        val paddingValues = it
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp, top = 70.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .shadow(10.dp, RoundedCornerShape(10.dp))
                        .width(310.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(9.dp, Color.Transparent, RoundedCornerShape(10.dp)),
                    value = search,
                    onValueChange = { viewModel.onSearchChanged(it) },
                    placeholder = {
                        Text(
                            "Buscar",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = Color.Gray,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(10.dp)
                )

                // Botón de búsqueda
                IconButton(
                    onClick = {
                        viewModel.getNews() // Llamamos a la función getNews() del ViewModel
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.Blue, shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error.isNotEmpty()) {
                Text(state.error)
            }
            state.data?.let { news: List<News> ->
                LazyColumn {
                    items(news) { new ->
                        Card(modifier = Modifier.padding(4.dp)) {
                            Column {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    GlideImage(
                                        modifier = Modifier
                                            .height(144.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        imageModel = { new.urlToImage },
                                        imageOptions = ImageOptions(
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center
                                        )
                                    )

                                }

                                Text(
                                    modifier = Modifier.padding(4.dp),
                                    text = new.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(modifier = Modifier.padding(4.dp), text = new.author)
                                Text(modifier = Modifier.padding(4.dp), text = new.year.toString())
                                IconButton(onClick = {
                                    try {
                                        val intent = Intent(context, NewsDetailActivity::class.java).apply {
                                            putExtra("newsTitle", new.title)
                                            putExtra("newsSource", new.sourceName)
                                            putExtra("newsImageUrl", new.urlToImage)
                                            putExtra("newsContent", new.content)
                                            putExtra("newsUrl", new.url)
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                },
                                    modifier = Modifier.fillMaxSize(),
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Blue)
                                ) {
                                    Text("Details", color = Color.White)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}