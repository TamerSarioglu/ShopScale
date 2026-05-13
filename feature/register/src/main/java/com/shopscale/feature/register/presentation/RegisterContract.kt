package com.shopscale.feature.register.presentation

import com.shopscale.core.common.mvi.UiEffect
import com.shopscale.core.common.mvi.UiEvent
import com.shopscale.core.common.mvi.UiState

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val avatar: String = "https://picsum.photos/800",
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed interface RegisterEvent : UiEvent {
    data class OnNameChanged(val name: String) : RegisterEvent
    data class OnEmailChanged(val email: String) : RegisterEvent
    data class OnPasswordChanged(val password: String) : RegisterEvent
    data class OnAvatarChanged(val avatar: String) : RegisterEvent
    data object OnRegisterClicked : RegisterEvent
}

sealed interface RegisterEffect : UiEffect {
    data object NavigateToMain : RegisterEffect
    data class ShowError(val message: String) : RegisterEffect
}
