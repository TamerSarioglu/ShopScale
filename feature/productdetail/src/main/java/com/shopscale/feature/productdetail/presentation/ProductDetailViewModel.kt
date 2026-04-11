package com.shopscale.feature.productdetail.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.feature.productdetail.domain.usecase.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : BaseViewModel<ProductDetailState, ProductDetailEvent, ProductDetailEffect>() {

    override fun createInitialState() = ProductDetailState()

    override fun onEvent(event: ProductDetailEvent) {
        when (event) {
            is ProductDetailEvent.LoadProductDetail -> fetchProductDetail(event.productId)
            is ProductDetailEvent.OnBackClicked -> setEffect(ProductDetailEffect.NavigateBack)
            is ProductDetailEvent.OnAddToCartClicked -> {
                setEffect(ProductDetailEffect.ShowSnackBar("Ürün sepete eklendi!"))
            }
        }
    }

    private fun fetchProductDetail(productId: Int) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            getProductDetailUseCase(productId)
                .onSuccess { productDetail ->
                    setState { copy(isLoading = false, product = productDetail) }
                }
                .onFailure { error ->
                    setState { copy(isLoading = false, error = error.message) }
                }
        }
    }
}