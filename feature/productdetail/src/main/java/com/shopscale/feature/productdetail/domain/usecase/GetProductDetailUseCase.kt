package com.shopscale.feature.productdetail.domain.usecase

import com.shopscale.feature.productdetail.domain.model.ProductDetail
import com.shopscale.feature.productdetail.domain.repository.ProductDetailRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductDetailRepository
) {
    suspend operator fun invoke(productId: Int): Result<ProductDetail> {
        return repository.getProductDetail(productId)
    }
}