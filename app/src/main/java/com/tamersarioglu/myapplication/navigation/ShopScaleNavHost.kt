package com.tamersarioglu.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shopscale.feature.auth.presentation.LoginScreen
import com.shopscale.feature.productdetail.presentation.ProductDetailScreen
import com.shopscale.feature.register.presentation.RegisterScreen

@Composable
fun ShopScaleNavHost() {
    val backStack = remember { mutableStateListOf<Any>(SplashRoute) }

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLast() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<SplashRoute> {
                SplashScreen(
                    onNavigateToLogin = {
                        backStack.clear()
                        backStack.add(LoginRoute)
                    },
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(MainRoute)
                    }
                )
            }

            entry<LoginRoute> {
                LoginScreen(
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(MainRoute)
                    },
                    onNavigateToRegister = {
                        backStack.add(RegisterRoute)
                    }
                )
            }

            entry<RegisterRoute> {
                RegisterScreen(
                    onNavigateBack = {
                        if (backStack.size > 1) backStack.removeLast()
                    },
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(MainRoute)
                    }
                )
            }

            entry<MainRoute> {
                MainScreen(
                    onNavigateToProductDetail = { productId ->
                        backStack.add(ProductDetailRoute(productId))
                    },
                    onNavigateToLogin = {
                        backStack.clear()
                        backStack.add(LoginRoute)
                    }
                )
            }

            entry<ProductDetailRoute> { route ->
                ProductDetailScreen(
                    productId = route.productId,
                    onNavigateBack = {
                        if (backStack.size > 1) backStack.removeLast()
                    }
                )
            }
        }
    )
}
