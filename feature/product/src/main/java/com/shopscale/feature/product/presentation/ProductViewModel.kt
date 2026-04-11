package com.shopscale.feature.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopscale.feature.product.domain.usecase.GetProductsUseCase
import com.shopscale.feature.product.domain.usecase.SyncProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val syncProductsUseCase: SyncProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProductEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observeProducts()
        onEvent(ProductEvent.OnRefresh)
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.OnRefresh -> syncData()
            is ProductEvent.OnProductClicked -> {
                viewModelScope.launch { _effect.emit(ProductEffect.NavigateToDetail(event.productId)) }
            }
        }
    }

    private fun observeProducts() {
        viewModelScope.launch {
            getProductsUseCase()
                .collect { products ->
                    if (products.isEmpty()) return@collect

                    val shuffled = products.shuffled()
                    _state.update { currentState ->
                        currentState.copy(
                            carouselProducts = shuffled.take(4),
                            hotDeals = shuffled.drop(4).take(3),
                            categorizedProducts = shuffled.drop(7).groupBy { it.description.take(10) } // Örnek gruplama
                        )
                    }
                }
        }
    }

    private fun syncData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            syncProductsUseCase()
            _state.update { it.copy(isLoading = false) }
        }
    }
}