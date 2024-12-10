package pe.edu.upc.newsly.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.newsly.domain.News

@Composable
fun FavoriteNewsScreen(viewModel: FindNewsViewModel) {
    val state = viewModel.stateFav.value

    LaunchedEffect(Unit) {
        viewModel.getFavoriteNews()
    }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(text = "Favorite News Screen", modifier = Modifier.padding(paddingValues))
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error.isNotEmpty()) {
                Text(state.error)
            }
            state.data?.let { news: List<News> ->
                LazyColumn {
                    items(news) { new->
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
                                    IconButton(
                                        onClick = {
                                            viewModel.toggleFavorite(new)
                                        }, modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(2.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Favorite,
                                            "Favorite",
                                            tint = if (new.isFavorite) Color.Red else Color.Gray
                                        )
                                    }

                                }

                                Text(
                                    modifier = Modifier.padding(4.dp),
                                    text = new.author,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(modifier = Modifier.padding(4.dp), text = new.title)
                                Text(modifier = Modifier.padding(4.dp), text = new.description)
                            }

                        }
                    }
                }
            }
        }

    }

}