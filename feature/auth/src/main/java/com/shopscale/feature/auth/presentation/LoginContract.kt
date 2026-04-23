package com.shopscale.feature.auth.presentation

import com.shopscale.core.common.mvi.UiEffect
import com.shopscale.core.common.mvi.UiEvent
import com.shopscale.core.common.mvi.UiState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed interface LoginEvent : UiEvent {
    data class OnEmailChanged(val email: String) : LoginEvent
    data class OnPasswordChanged(val password: String) : LoginEvent
    data object OnLoginClicked : LoginEvent
}

sealed interface LoginEffect : UiEffect {
    data object NavigateToMain : LoginEffect
    data class ShowError(val message: String) : LoginEffect
}
