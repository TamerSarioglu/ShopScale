package com.shopscale.feature.product.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shopscale.feature.product.presentation.composables.ProductCard
import com.shopscale.feature.product.presentation.composables.ProductListItem

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProductEffect.NavigateToDetail -> onNavigateToDetail(effect.productId)
                is ProductEffect.ShowError -> {
                    println("Error: ${effect.message}")
                }
            }
        }
    }

    Scaffold { paddingValues ->
        when {
            state.isLoading && state.carouselProducts.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    if (state.carouselProducts.isNotEmpty()) {
                        item {
                            Text(
                                text = "🔥 Öne Çıkanlar",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(state.carouselProducts) { product ->
                                    ProductCard(product) {
                                        viewModel.onEvent(ProductEvent.OnProductClicked(it))
                                    }
                                }
                            }
                        }
                    }

                    if (state.hotDeals.isNotEmpty()) {
                        item {
                            Text(
                                text = "⚡ Günün Fırsatları",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(
                                    top = 24.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(state.hotDeals) { product ->
                                    ProductCard(product) {
                                        viewModel.onEvent(ProductEvent.OnProductClicked(it))
                                    }
                                }
                            }
                        }
                    }

                    state.categorizedProducts.forEach { (categoryName, products) ->
                        item {
                            Text(
                                text = categoryName,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(
                                    top = 24.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 8.dp
                                )
                            )
                        }
                        items(products) { product ->
                            ProductListItem(product) {
                                viewModel.onEvent(ProductEvent.OnProductClicked(it))
                            }
                        }
                    }
                }
            }
        }
    }
}