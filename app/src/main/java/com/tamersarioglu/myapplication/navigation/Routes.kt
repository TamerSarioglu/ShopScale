package com.tamersarioglu.myapplication.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ProductListRoute

@Serializable
data class ProductDetailRoute(val productId: Int)
