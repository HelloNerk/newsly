package pe.edu.upc.newsly.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.edu.upc.newsly.R
import pe.edu.upc.newsly.common.Screens

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "The instant news",
                modifier = Modifier.padding(16.dp),
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Image(painter = painterResource(id = R.drawable.logo_newsly), contentDescription = "Logo")
            Button(
                onClick = { navController.navigate(Screens.FavoriteNews.route) },
                modifier = Modifier.padding(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ){
                Text(text = "Favorite News")
            }

            Button(onClick = {
                navController.navigate(Screens.FindNews.route) },
                modifier = Modifier.padding(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ){
                Text(text = "Find News")
            }
        }
    }
}
