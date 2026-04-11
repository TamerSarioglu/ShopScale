package com.tamersarioglu.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.shopscale.core.network.api.ShopScaleAuthApi
import com.shopscale.core.network.model.dto.LoginRequestDto
import com.shopscale.feature.product.domain.repository.ProductRepository
import com.shopscale.feature.product.presentation.ProductScreen
import com.tamersarioglu.myapplication.ui.theme.ShopScaleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authApi: ShopScaleAuthApi

    @Inject
    lateinit var productRepository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            productRepository.getProducts().collect { products ->
                Log.d("TestOfflineFirst", "Veritabanından gelen ürün sayısı: ${products.size}")
                if (products.isNotEmpty()) {
                    Log.d("TestOfflineFirst", "İlk Ürün: ${products.first().title} - $${products.first().price}")
                }
            }
        }

        lifecycleScope.launch {
            Log.d("TestOfflineFirst", "İnternetten veri çekme/senkronizasyon başlatılıyor...")
            productRepository.syncProducts()
            Log.d("TestOfflineFirst", "Senkronizasyon emri tamamlandı.")
        }


        setContent {
            ShopScaleTheme {
                ProductScreen { productId ->
                    Log.d("Navigation", "Ürün Detayına Navigasyon: Product ID = $productId")
                }
            }
        }
    }
}