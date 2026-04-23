package com.tamersarioglu.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tamersarioglu.myapplication.navigation.ShopScaleNavHost
import com.tamersarioglu.myapplication.ui.theme.ShopScaleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopScaleTheme {
                ShopScaleNavHost()
            }
        }
    }
}
