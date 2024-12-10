package pe.edu.upc.newsly.common

sealed class Screens(val route:String) {
    object Home: Screens("home_screen")
    object FindNews: Screens("find_news_screen")
    object FavoriteNews: Screens("favorite_news_screen")

}