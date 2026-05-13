package com.shopscale.feature.product.domain.repository

import com.shopscale.feature.product.domain.model.Category
import com.shopscale.feature.product.domain.model.ProductFilter
import com.shopscale.feature.product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun getCategories(): Result<List<Category>>
    suspend fun syncProducts(filter: ProductFilter = ProductFilter()): Result<Unit>
}
