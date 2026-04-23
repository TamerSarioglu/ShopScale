package com.shopscale.feature.settings.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.feature.settings.domain.usecase.GetUserProfileUseCase
import com.shopscale.feature.settings.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase
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
            getUserProfileUseCase()
                .onSuccess { profile ->
                    setState { copy(isLoading = false, user = profile) }
                }
                .onFailure { error ->
                    setState { copy(isLoading = false, error = error.message ?: "Failed to load profile") }
                    setEffect(SettingsEffect.ShowError(error.message ?: "Failed to load profile"))
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            setEffect(SettingsEffect.NavigateToLogin)
        }
    }
}
