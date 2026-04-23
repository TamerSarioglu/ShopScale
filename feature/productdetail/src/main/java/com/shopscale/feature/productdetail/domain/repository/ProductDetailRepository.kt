package com.shopscale.feature.productdetail.domain.repository

import com.shopscale.feature.productdetail.domain.model.ProductDetail

interface ProductDetailRepository {
    suspend fun getProductDetail(productId: Int): Result<ProductDetail>
}