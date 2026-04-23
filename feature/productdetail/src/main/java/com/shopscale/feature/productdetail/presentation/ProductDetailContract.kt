package com.shopscale.feature.productdetail.presentation

import com.shopscale.core.common.mvi.UiEffect
import com.shopscale.core.common.mvi.UiEvent
import com.shopscale.core.common.mvi.UiState
import com.shopscale.feature.productdetail.domain.model.ProductDetail

data class ProductDetailState(
    val isLoading: Boolean = true,
    val product: ProductDetail? = null,
    val error: String? = null
) : UiState

sealed interface ProductDetailEvent : UiEvent {
    data class LoadProductDetail(val productId: Int) : ProductDetailEvent
    object OnBackClicked : ProductDetailEvent
    object OnAddToCartClicked : ProductDetailEvent
}

sealed interface ProductDetailEffect : UiEffect {
    object NavigateBack : ProductDetailEffect
    data class ShowSnackBar(val message: String) : ProductDetailEffect
}