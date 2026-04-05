package com.shopscale.feature.product.data.repository

import com.shopscale.core.database.dao.ProductDao
import com.shopscale.feature.product.data.mapper.toDomain
import com.shopscale.feature.product.data.mapper.toEntity
import com.shopscale.feature.product.data.remote.api.ProductApi
import com.shopscale.feature.product.domain.model.Product
import com.shopscale.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> {
        return dao.getAllProducts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncProducts() {
        try {
            val dtoList = api.fetchProducts()
            val entities = dtoList.map { it.toEntity() }
            dao.clearAndInsertProducts(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
