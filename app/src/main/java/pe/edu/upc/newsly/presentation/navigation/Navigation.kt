package pe.edu.upc.newsly.presentation.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.newsly.common.Screens
import pe.edu.upc.newsly.presentation.FavoriteNewsScreen
import pe.edu.upc.newsly.presentation.FindNewsScreen
import pe.edu.upc.newsly.presentation.FindNewsViewModel
import pe.edu.upc.newsly.presentation.HomeScreen


@Composable
fun Navigation(viewModel: FindNewsViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(route = Screens.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.FindNews.route) {
            FindNewsScreen(viewModel)
        }
        composable(route = Screens.FavoriteNews.route) {
            FavoriteNewsScreen(viewModel)
        }
    }

}

