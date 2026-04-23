package com.tamersarioglu.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shopscale.feature.product.presentation.ProductScreen
import com.shopscale.feature.settings.presentation.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProductListTab

@Serializable
data object SettingsTab

@Composable
fun MainScreen(
    onNavigateToProductDetail: (Int) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val navController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute("Products", ProductListTab, Icons.Default.Home),
        TopLevelRoute("Settings", SettingsTab, Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry.value?.destination

                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                        label = { Text(topLevelRoute.name) },
                        selected = currentDestination?.hasRoute(topLevelRoute.route::class) == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ProductListTab,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<ProductListTab> {
                ProductScreen(
                    onNavigateToDetail = onNavigateToProductDetail
                )
            }
            composable<SettingsTab> {
                SettingsScreen(
                    onNavigateToLogin = onNavigateToLogin
                )
            }
        }
    }
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: androidx.compose.ui.graphics.vector.ImageVector)
