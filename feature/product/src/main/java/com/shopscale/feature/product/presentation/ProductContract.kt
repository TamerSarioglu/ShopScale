package com.shopscale.feature.product.presentation

import com.shopscale.feature.product.domain.model.Product

data class ProductState(
    val isLoading: Boolean = false,
    val carouselProducts: List<Product> = emptyList(),
    val hotDeals: List<Product> = emptyList(),
    val categorizedProducts: Map<String, List<Product>> = emptyMap(),
    val error: String? = null
)

sealed interface ProductEvent {
    data object OnRefresh : ProductEvent
    data class OnProductClicked(val productId: Int) : ProductEvent
}

sealed interface ProductEffect {
    data class NavigateToDetail(val productId: Int) : ProductEffect
    data class ShowError(val message: String) : ProductEffect
}