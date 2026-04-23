package com.shopscale.feature.settings.presentation

import com.shopscale.core.common.mvi.UiEffect
import com.shopscale.core.common.mvi.UiEvent
import com.shopscale.core.common.mvi.UiState

data class UserProfile(
    val id: Int,
    val email: String,
    val name: String,
    val role: String,
    val avatar: String
)

data class SettingsState(
    val user: UserProfile? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed interface SettingsEvent : UiEvent {
    data object OnLogoutClicked : SettingsEvent
    data object OnRetryClicked : SettingsEvent
}

sealed interface SettingsEffect : UiEffect {
    data object NavigateToLogin : SettingsEffect
    data class ShowError(val message: String) : SettingsEffect
}
