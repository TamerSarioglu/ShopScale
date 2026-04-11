package com.shopscale.feature.productdetail.data.mapper


import com.shopscale.feature.productdetail.data.remote.dto.ProductDetailDto
import com.shopscale.feature.productdetail.domain.model.ProductDetail

fun ProductDetailDto.toDomain(): ProductDetail {
    return ProductDetail(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        price = this.price ?: 0.0,
        description = this.description.orEmpty(),
        images = this.images ?: emptyList(),
        categoryName = this.category?.name.orEmpty()
    )
}