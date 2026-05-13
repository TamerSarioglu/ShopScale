package com.shopscale.feature.product.presentation

import com.shopscale.core.common.mvi.UiEffect
import com.shopscale.core.common.mvi.UiEvent
import com.shopscale.core.common.mvi.UiState
import com.shopscale.feature.product.domain.model.Category
import com.shopscale.feature.product.domain.model.Product

data class ProductState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: Int? = null,
    val titleQuery: String = "",
    val minPrice: String = "",
    val maxPrice: String = "",
    val products: List<Product> = emptyList(),
    val carouselProducts: List<Product> = emptyList(),
    val hotDeals: List<Product> = emptyList(),
    val categorizedProducts: Map<String, List<Product>> = emptyMap(),
    val error: String? = null
) : UiState

sealed interface ProductEvent : UiEvent {
    data object OnRefresh : ProductEvent
    data class OnTitleQueryChanged(val query: String) : ProductEvent
    data class OnMinPriceChanged(val price: String) : ProductEvent
    data class OnMaxPriceChanged(val price: String) : ProductEvent
    data class OnCategorySelected(val categoryId: Int?) : ProductEvent
    data object OnApplyFilters : ProductEvent
    data object OnClearFilters : ProductEvent
    data class OnProductClicked(val productId: Int) : ProductEvent
}

sealed interface ProductEffect : UiEffect {
    data class NavigateToDetail(val productId: Int) : ProductEffect
    data class ShowError(val message: String) : ProductEffect
}
