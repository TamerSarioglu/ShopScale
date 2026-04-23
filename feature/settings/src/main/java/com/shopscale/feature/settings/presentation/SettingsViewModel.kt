package com.shopscale.feature.settings.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.core.network.TokenManager
import com.shopscale.feature.settings.data.remote.api.SettingsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsApi: SettingsApi,
    private val tokenManager: TokenManager
) : BaseViewModel<SettingsState, SettingsEvent, SettingsEffect>() {

    override fun createInitialState() = SettingsState()

    init {
        loadProfile()
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnLogoutClicked -> logout()
            is SettingsEvent.OnRetryClicked -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                val profile = settingsApi.getProfile()
                setState {
                    copy(
                        isLoading = false,
                        user = UserProfile(
                            id = profile.id,
                            email = profile.email,
                            name = profile.name,
                            role = profile.role,
                            avatar = profile.avatar
                        )
                    )
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message ?: "Failed to load profile") }
                setEffect(SettingsEffect.ShowError(e.message ?: "Failed to load profile"))
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            tokenManager.clearTokens()
            setEffect(SettingsEffect.NavigateToLogin)
        }
    }
}
