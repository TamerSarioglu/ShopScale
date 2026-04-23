package com.shopscale.feature.productdetail.domain.model

data class ProductDetail(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val images: List<String>,
    val categoryName: String
)