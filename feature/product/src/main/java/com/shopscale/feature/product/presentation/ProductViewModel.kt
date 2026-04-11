package com.shopscale.feature.product.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.feature.product.domain.usecase.GetProductsUseCase
import com.shopscale.feature.product.domain.usecase.SyncProductsUseCase
import com.shopscale.feature.product.presentation.contract.ProductEffect
import com.shopscale.feature.product.presentation.contract.ProductEvent
import com.shopscale.feature.product.presentation.contract.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val syncProductsUseCase: SyncProductsUseCase
) : BaseViewModel<ProductState, ProductEvent, ProductEffect>() {

    override fun createInitialState() = ProductState()

    init {
        observeProducts()
        onEvent(ProductEvent.OnRefresh)
    }

    override fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.OnRefresh -> syncData()
            is ProductEvent.OnProductClicked -> setEffect(ProductEffect.NavigateToDetail(event.productId))
        }
    }

    private fun observeProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { products ->
                if (products.isEmpty()) return@collect

                val shuffled = products.shuffled()
                setState {
                    copy(
                        carouselProducts = shuffled.take(4),
                        hotDeals = shuffled.drop(4).take(3),
                        categorizedProducts = shuffled.drop(7).groupBy { it.description.take(10) }
                    )
                }
            }
        }
    }

    private fun syncData() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            syncProductsUseCase()
            setState { copy(isLoading = false) }
        }
    }
}
