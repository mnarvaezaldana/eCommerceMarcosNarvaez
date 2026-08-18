package com.yucatancorp.ecommercemarcosnarvaez.utils

import com.yucatancorp.ecommercemarcosnarvaez.data.ProductDomain
import com.yucatancorp.ecommercemarcosnarvaez.presentation.ProductUI

fun List<ProductDomain>.toProductUI(): List<ProductUI> {
    return map { product ->
        ProductUI(
            name = product.title,
            price = product.price,
            urlImage = product.imageUrl,
            offerId = product.offerId
        )
    }
}