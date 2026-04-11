package com.tamersarioglu.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shopscale.feature.product.presentation.ProductScreen
import com.shopscale.feature.productdetail.presentation.ProductDetailScreen

@Composable
fun ShopScaleNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ProductListRoute
    ) {
        composable<ProductListRoute> {
            ProductScreen(
                onNavigateToDetail = { productId ->
                    navController.navigate(ProductDetailRoute(productId))
                }
            )
        }

        composable<ProductDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ProductDetailRoute>()
            ProductDetailScreen(
                productId = route.productId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
