package com.yucatancorp.ecommercemarcosnarvaez.presentation

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
    private var activeQuery: String = ""
    private var searchVersion: Int = 0

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
        if (query.isEmpty()) {
            return
        }
        activeQuery = query
        searchVersion++
        searchHistoryManager.saveSearch(query)
        loadSearchHistory()
        _uiState.update {
            it.copy(
                products = emptyList(),
                currentPage = 0,
                hasMorePages = true,
                isLoading = false
            )
        }

        loadNextPage()
    }

    fun loadNextPage() {

        val state = _uiState.value

        if (
            state.isLoading ||
            !state.hasMorePages ||
            activeQuery.isBlank()
        ) {
            return
        }

        val query = activeQuery
        val nextPage = state.currentPage + 1
        val currentSearchVersion = searchVersion

        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val data = useCase(API_KEY, query, nextPage)
                val newProducts = data.toProductUI()

                if (currentSearchVersion != searchVersion) {
                    return@launch
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        products = (currentState.products + newProducts).distinctBy { it.offerId },
                        currentPage = nextPage,
                        isLoading = false,
                        hasMorePages = newProducts.isNotEmpty()
                    )
                }

            } catch (e: Exception) {
                if (currentSearchVersion != searchVersion) {
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
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