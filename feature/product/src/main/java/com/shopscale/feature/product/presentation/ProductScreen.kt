package com.shopscale.feature.product.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shopscale.feature.product.domain.model.Category
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
                        .padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        ProductFilters(
                            titleQuery = state.titleQuery,
                            minPrice = state.minPrice,
                            maxPrice = state.maxPrice,
                            categories = state.categories,
                            selectedCategoryId = state.selectedCategoryId,
                            isLoading = state.isLoading,
                            onTitleQueryChanged = {
                                viewModel.onEvent(ProductEvent.OnTitleQueryChanged(it))
                            },
                            onMinPriceChanged = {
                                viewModel.onEvent(ProductEvent.OnMinPriceChanged(it))
                            },
                            onMaxPriceChanged = {
                                viewModel.onEvent(ProductEvent.OnMaxPriceChanged(it))
                            },
                            onCategorySelected = {
                                viewModel.onEvent(ProductEvent.OnCategorySelected(it))
                            },
                            onApplyFilters = {
                                viewModel.onEvent(ProductEvent.OnApplyFilters)
                            },
                            onClearFilters = {
                                viewModel.onEvent(ProductEvent.OnClearFilters)
                            }
                        )
                    }

                    if (state.carouselProducts.isNotEmpty()) {
                        item {
                            Text(
                                text = "Featured Products",
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
                                text = "Today Deals",
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

                    if (!state.isLoading && state.carouselProducts.isEmpty()) {
                        item {
                            Text(
                                text = "No products found",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductFilters(
    titleQuery: String,
    minPrice: String,
    maxPrice: String,
    categories: List<Category>,
    selectedCategoryId: Int?,
    isLoading: Boolean,
    onTitleQueryChanged: (String) -> Unit,
    onMinPriceChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
    onCategorySelected: (Int?) -> Unit,
    onApplyFilters: () -> Unit,
    onClearFilters: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Explore products",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = titleQuery,
            onValueChange = onTitleQueryChanged,
            label = { Text("Search by title") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = minPrice,
                onValueChange = onMinPriceChanged,
                label = { Text("Min") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = maxPrice,
                onValueChange = onMaxPriceChanged,
                label = { Text("Max") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        if (categories.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 1.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedCategoryId == null,
                        onClick = { onCategorySelected(null) },
                        label = { Text("All") }
                    )
                }
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategoryId == category.id,
                        onClick = { onCategorySelected(category.id) },
                        label = { Text(category.name) }
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onApplyFilters,
                enabled = !isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text("Apply")
            }
            OutlinedButton(
                onClick = onClearFilters,
                enabled = !isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear")
            }
        }

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}
