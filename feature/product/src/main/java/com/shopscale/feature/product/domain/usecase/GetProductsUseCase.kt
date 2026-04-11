package com.shopscale.feature.product.domain.usecase

import com.shopscale.feature.product.domain.model.Product
import com.shopscale.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke() : Flow<List<Product>> {
        return repository.getProducts()
    }
}