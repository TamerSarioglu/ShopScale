package com.shopscale.feature.product.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val isAvailable: Boolean
)
