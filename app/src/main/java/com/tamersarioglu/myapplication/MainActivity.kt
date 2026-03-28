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
import com.tamersarioglu.myapplication.ui.theme.ShopScaleTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authApi: ShopScaleAuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            try {
                Log.d("ShopScaleTest", "Sending Test Login Request...")
                val response = authApi.login(
                    LoginRequestDto("tamer@test.com", "123456")
                )
                Log.d("ShopScaleTest", "Login Success: ${response.accessToken}")
            } catch (e: Exception) {
                Log.e("ShopScaleTest", "Request Failed as Expected: ${e.message}")
            }
        }

        setContent {
            ShopScaleTheme {
                Text(text = "Hello ShopScale! Check Logcat for Network Logs.")
            }
        }
    }
}