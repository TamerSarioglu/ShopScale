package com.shopscale.feature.product.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.feature.product.domain.model.Product
import com.shopscale.feature.product.domain.model.ProductFilter
import com.shopscale.feature.product.domain.usecase.GetCategoriesUseCase
import com.shopscale.feature.product.domain.usecase.GetProductsUseCase
import com.shopscale.feature.product.domain.usecase.SyncProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val syncProductsUseCase: SyncProductsUseCase
) : BaseViewModel<ProductState, ProductEvent, ProductEffect>() {

    override fun createInitialState() = ProductState()

    init {
        observeProducts()
        loadCategories()
        onEvent(ProductEvent.OnRefresh)
    }

    override fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.OnRefresh -> syncData()
            is ProductEvent.OnTitleQueryChanged -> setState { copy(titleQuery = event.query) }
            is ProductEvent.OnMinPriceChanged -> setState {
                copy(minPrice = event.price.filter { it.isDigit() })
            }
            is ProductEvent.OnMaxPriceChanged -> setState {
                copy(maxPrice = event.price.filter { it.isDigit() })
            }
            is ProductEvent.OnCategorySelected -> {
                setState { copy(selectedCategoryId = event.categoryId) }
                syncData()
            }
            is ProductEvent.OnApplyFilters -> syncData()
            is ProductEvent.OnClearFilters -> {
                setState {
                    copy(
                        selectedCategoryId = null,
                        titleQuery = "",
                        minPrice = "",
                        maxPrice = ""
                    )
                }
                syncData(ProductFilter())
            }
            is ProductEvent.OnProductClicked -> setEffect(ProductEffect.NavigateToDetail(event.productId))
        }
    }

    private fun observeProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { products ->
                if (products.isEmpty()) return@collect

                applyProducts(products)
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onSuccess { categories ->
                    setState { copy(categories = categories) }
                    applyProducts(state.value.products)
                }
                .onFailure { error ->
                    setEffect(ProductEffect.ShowError(error.message ?: "Failed to load categories"))
                }
        }
    }

    private fun applyProducts(products: List<Product>) {
        val shuffled = products.shuffled()
        setState {
            val categoryNamesById = categories.associate { it.id to it.name }
            copy(
                products = products,
                carouselProducts = shuffled.take(4),
                hotDeals = shuffled.drop(4).take(3),
                categorizedProducts = shuffled.drop(7).groupBy { product ->
                    categoryNamesById[product.categoryId] ?: "Other Products"
                }
            )
        }
    }

    private fun syncData(filter: ProductFilter = currentFilter()) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            syncProductsUseCase(filter)
                .onSuccess {
                    setState { copy(isLoading = false, error = null) }
                }
                .onFailure { error ->
                    val message = error.message ?: "Failed to load products"
                    setState { copy(isLoading = false, error = message) }
                    setEffect(ProductEffect.ShowError(message))
                }
        }
    }

    private fun currentFilter(): ProductFilter {
        val currentState = state.value
        return ProductFilter(
            title = currentState.titleQuery.takeIf { it.isNotBlank() },
            priceMin = currentState.minPrice.toIntOrNull(),
            priceMax = currentState.maxPrice.toIntOrNull(),
            categoryId = currentState.selectedCategoryId,
            limit = 50,
            offset = 0
        )
    }
}
