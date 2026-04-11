package com.shopscale.feature.product.domain.usecase

import com.shopscale.feature.product.domain.repository.ProductRepository
import javax.inject.Inject

class SyncProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() {
        repository.syncProducts()
    }
}