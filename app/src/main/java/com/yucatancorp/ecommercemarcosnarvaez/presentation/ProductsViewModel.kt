package com.yucatancorp.ecommercemarcosnarvaez.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yucatancorp.ecommercemarcosnarvaez.domain.GetProductUseCase
import com.yucatancorp.ecommercemarcosnarvaez.domain.SearchHistoryManager
import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.API_KEY
import com.yucatancorp.ecommercemarcosnarvaez.utils.toProductUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val useCase: GetProductUseCase,
    private val searchHistoryManager: SearchHistoryManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    init {
        loadSearchHistory()
    }

    fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun search() {
        val query = _uiState.value.query.trim()

        if (query.isEmpty())
            return

        searchHistoryManager.saveSearch(query)
        _uiState.update {
            it.copy(
                products = emptyList(),
                currentPage = 0,
                hasMorePages = true
            )
        }

        loadNextPage()
    }

    fun loadNextPage() {
        val state = _uiState.value

        if (state.isLoading || !state.hasMorePages)
            return

        val nextPage = state.currentPage + 1
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val data = useCase(API_KEY, state.query, nextPage)
                _uiState.update { currentState ->
                    currentState.copy(
                        products = (currentState.products + data.toProductUI()) as List<ProductUI>,
                        currentPage = nextPage,
                        isLoading = false,
                        hasMorePages = data.isNotEmpty()
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(isLoading = false)
                }

                Log.e("ProductsViewModel", "Error", e)
            }
        }
    }

    private fun loadSearchHistory() {
        _searchHistory.value = searchHistoryManager.getSearchHistory()
    }

    fun clearSearchHistory() {
        searchHistoryManager.clearHistory()
        _searchHistory.value = emptyList()
    }
}