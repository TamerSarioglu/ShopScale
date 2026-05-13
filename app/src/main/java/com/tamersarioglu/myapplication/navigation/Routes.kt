package com.tamersarioglu.myapplication.navigation

import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute

@Serializable
data object MainRoute

@Serializable
data class ProductDetailRoute(val productId: Int)
