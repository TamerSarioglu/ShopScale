package com.shopscale.feature.product.data.mapper

import com.shopscale.core.database.entity.ProductEntity
import com.shopscale.feature.product.data.remote.dto.ProductDto
import com.shopscale.feature.product.data.remote.dto.CategoryDto
import com.shopscale.feature.product.domain.model.Category
import com.shopscale.feature.product.domain.model.Product

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        price = this.price ?: 0.0,
        description = this.description.orEmpty(),
        imageUrl = this.images?.firstOrNull().orEmpty(),
        categoryId = this.category?.id
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        imageUrl = this.imageUrl,
        categoryId = this.categoryId,
        isAvailable = this.categoryId != null
    )
}

fun CategoryDto.toDomain(): Category {
    return Category(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        slug = this.slug.orEmpty(),
        imageUrl = this.image.orEmpty()
    )
}
