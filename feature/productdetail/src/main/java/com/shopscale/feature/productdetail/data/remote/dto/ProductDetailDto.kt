package com.shopscale.feature.productdetail.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int? = null,
    val name: String? = null,
    val image: String? = null,
    val slug: String? = null
)

@Serializable
data class ProductDetailDto(
    val id: Int? = null,
    val title: String? = null,
    val slug: String? = null,
    val price: Double? = null,
    val description: String? = null,
    val category: CategoryDto? = null,
    val images: List<String>? = null
)