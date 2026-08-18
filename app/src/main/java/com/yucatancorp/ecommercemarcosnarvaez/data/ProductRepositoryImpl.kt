package com.yucatancorp.ecommercemarcosnarvaez.data

import com.yucatancorp.ecommercemarcosnarvaez.domain.ProductsRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val api: ProductsService
): ProductsRepository {

    override suspend fun getProducts(
        apiKey: String,
        query: String,
        page: Int
    ): List<ProductDomain> {
        val data = mutableListOf<ProductDomain>()
        api.searchByKeyword(
            apiKey = apiKey,
            keyword = query,
            page = page
        ).item?.props?.pageProps?.initialData?.searchResult?.itemStacks?.firstOrNull()?.items?.forEach {

            val name = it.name
            val price = it.priceInfo?.priceDetails?.priceLines?.firstOrNull()?.values?.firstOrNull()?.value
            val imageUrl = it.image
            val offerId = it.offerId

            if (name != null && price != null && imageUrl != null && offerId != null) {
                data.add(
                    ProductDomain(
                        title = name,
                        price = price,
                        imageUrl = imageUrl,
                        offerId = offerId
                    )
                )
            }
        }

        return data
    }
}