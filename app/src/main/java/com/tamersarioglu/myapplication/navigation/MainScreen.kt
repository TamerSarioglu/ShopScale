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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
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
    val tabBackStack = remember { mutableStateListOf<Any>(ProductListTab) }
    val currentTab = tabBackStack.lastOrNull() ?: ProductListTab

    val topLevelRoutes = listOf(
        TopLevelRoute("Products", ProductListTab, Icons.Default.Home),
        TopLevelRoute("Settings", SettingsTab, Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                        label = { Text(topLevelRoute.name) },
                        selected = currentTab::class == topLevelRoute.route::class,
                        onClick = {
                            if (currentTab != topLevelRoute.route) {
                                tabBackStack.clear()
                                tabBackStack.add(topLevelRoute.route)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = tabBackStack,
            onBack = { /* Root container handles exit/back pressed behavior */ },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            modifier = Modifier.padding(innerPadding),
            entryProvider = entryProvider {
                entry<ProductListTab> {
                    ProductScreen(
                        onNavigateToDetail = onNavigateToProductDetail
                    )
                }
                entry<SettingsTab> {
                    SettingsScreen(
                        onNavigateToLogin = onNavigateToLogin
                    )
                }
            }
        )
    }
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: androidx.compose.ui.graphics.vector.ImageVector)
