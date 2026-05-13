package com.shopscale.feature.product.domain.usecase

import com.shopscale.feature.product.domain.model.Category
import com.shopscale.feature.product.domain.repository.ProductRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Category>> {
        return repository.getCategories()
    }
}
