package com.yucatancorp.ecommercemarcosnarvaez.presentation

data class ProductsUiState(
    val query: String = "",
    val products: List<ProductUI> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val hasMorePages: Boolean = true
)

data class ProductUI(
    val name: String,
    val price: String,
    val urlImage: String,
    val offerId: String
)