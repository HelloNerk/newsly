package pe.edu.upc.newsly.common

data class UIState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String = ""
)