package com.shopscale.feature.product.data.repository

import com.shopscale.core.database.dao.ProductDao
import com.shopscale.feature.product.data.mapper.toDomain
import com.shopscale.feature.product.data.mapper.toEntity
import com.shopscale.feature.product.data.remote.api.ProductApi
import com.shopscale.feature.product.domain.model.Category
import com.shopscale.feature.product.domain.model.Product
import com.shopscale.feature.product.domain.model.ProductFilter
import com.shopscale.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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

    override suspend fun getCategories(): Result<List<Category>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(api.fetchCategories().map { it.toDomain() })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun syncProducts(filter: ProductFilter): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val dtoList = api.fetchProducts(
                    title = filter.title?.takeIf { it.isNotBlank() },
                    priceMin = filter.priceMin,
                    priceMax = filter.priceMax,
                    categoryId = filter.categoryId,
                    categorySlug = filter.categorySlug?.takeIf { it.isNotBlank() },
                    limit = filter.limit,
                    offset = filter.offset
                )
                val entities = dtoList.map { it.toEntity() }
                dao.clearAndInsertProducts(entities)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
