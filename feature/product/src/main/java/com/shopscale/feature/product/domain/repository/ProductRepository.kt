package com.shopscale.feature.product.domain.repository

import com.shopscale.feature.product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun syncProducts()
}
