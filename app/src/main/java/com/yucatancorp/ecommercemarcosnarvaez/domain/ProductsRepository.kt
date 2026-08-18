package com.yucatancorp.ecommercemarcosnarvaez.domain

import com.yucatancorp.ecommercemarcosnarvaez.data.ProductDomain

interface ProductsRepository {
    suspend fun getProducts(
        apiKey: String,
        query: String,
        page: Int
    ): List<ProductDomain>
}