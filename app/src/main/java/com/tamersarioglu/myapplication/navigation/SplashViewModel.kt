package com.tamersarioglu.myapplication.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopscale.core.network.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    tokenManager: TokenManager
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean?> = tokenManager.isLoggedIn
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}
