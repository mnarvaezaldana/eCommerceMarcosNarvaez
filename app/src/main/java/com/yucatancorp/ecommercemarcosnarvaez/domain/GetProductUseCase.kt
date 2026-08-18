package com.yucatancorp.ecommercemarcosnarvaez.domain

import com.yucatancorp.ecommercemarcosnarvaez.data.ProductDomain
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(
        apiKey: String,
        query: String,
        page: Int
    ): List<ProductDomain> {
        return repository
            .getProducts(apiKey, query, page)
            .map {
                ProductDomain(
                    title = it.title,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    offerId = it.offerId
                )
            }
    }
}