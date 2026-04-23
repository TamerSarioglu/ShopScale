package com.tamersarioglu.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shopscale.feature.auth.presentation.LoginScreen
import com.shopscale.feature.productdetail.presentation.ProductDetailScreen

@Composable
fun ShopScaleNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(MainRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<LoginRoute> {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(MainRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<MainRoute> {
            MainScreen(
                onNavigateToProductDetail = { productId ->
                    navController.navigate(ProductDetailRoute(productId))
                },
                onNavigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(MainRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<ProductDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ProductDetailRoute>()
            ProductDetailScreen(
                productId = route.productId,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
