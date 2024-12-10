package pe.edu.upc.newsly.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.newsly.common.Resource
import pe.edu.upc.newsly.common.UIState
import pe.edu.upc.newsly.data.repository.NewsRepository
import pe.edu.upc.newsly.domain.News

class FindNewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    private val _state = mutableStateOf(UIState<List<News>>())
    val state: State<UIState<List<News>>> get() = _state

    private val _stateFav = mutableStateOf(UIState<List<News>>())
    val stateFav: State<UIState<List<News>>> get() = _stateFav

    private val _search = mutableStateOf("")
    val search: State<String> get() = _search

    fun onSearchChanged(search: String) {
        _search.value = search
    }

    fun getNews() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = newsRepository.getNews(_search.value)
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(error = result.message ?: "An error occurred.")
            }
        }
    }

    fun getFavoriteNews() {
        _stateFav.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = newsRepository.getFavoriteNews()
            if (result is Resource.Success) {
                _stateFav.value = UIState(data = result.data)
            } else {
                _stateFav.value = UIState(error = result.message ?: "An error occurred.")
            }
        }

    }

    fun toggleFavorite(new: News){
        new.isFavorite = !new.isFavorite
        viewModelScope.launch {
            if (new.isFavorite) {
                newsRepository.insert(new)
            } else {
                newsRepository.delete(new)
            }
            val pckgs = _state.value.data
            _state.value = UIState(data = emptyList())
            _state.value = UIState(data = pckgs)
            _stateFav.value = UIState(data = emptyList())
            _stateFav.value = UIState(data = newsRepository.getFavoriteNews().data)
        }

    }
}