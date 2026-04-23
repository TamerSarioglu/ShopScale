package com.shopscale.feature.productdetail.data.repository

import com.shopscale.feature.productdetail.data.mapper.toDomain
import com.shopscale.feature.productdetail.data.remote.api.ProductDetailApi
import com.shopscale.feature.productdetail.domain.model.ProductDetail
import com.shopscale.feature.productdetail.domain.repository.ProductDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val api: ProductDetailApi
) : ProductDetailRepository {

    override suspend fun getProductDetail(productId: Int): Result<ProductDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val dto = api.getProductDetail(productId)
                Result.success(dto.toDomain())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}