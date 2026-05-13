package com.shopscale.feature.product.domain.model

data class ProductFilter(
    val title: String? = null,
    val priceMin: Int? = null,
    val priceMax: Int? = null,
    val categoryId: Int? = null,
    val categorySlug: String? = null,
    val limit: Int? = null,
    val offset: Int? = null
)
