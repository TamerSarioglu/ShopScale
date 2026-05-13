package com.shopscale.feature.register.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.core.network.TokenManager
import com.shopscale.core.network.api.ShopScaleAuthApi
import com.shopscale.core.network.model.dto.LoginRequestDto
import com.shopscale.core.network.model.dto.RegisterUserRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authApi: ShopScaleAuthApi,
    private val tokenManager: TokenManager
) : BaseViewModel<RegisterState, RegisterEvent, RegisterEffect>() {

    override fun createInitialState() = RegisterState()

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnNameChanged -> setState { copy(name = event.name, error = null) }
            is RegisterEvent.OnEmailChanged -> setState { copy(email = event.email, error = null) }
            is RegisterEvent.OnPasswordChanged -> setState { copy(password = event.password, error = null) }
            is RegisterEvent.OnAvatarChanged -> setState { copy(avatar = event.avatar, error = null) }
            is RegisterEvent.OnRegisterClicked -> register()
        }
    }

    private fun register() {
        val currentState = state.value
        if (
            currentState.name.isBlank() ||
            currentState.email.isBlank() ||
            currentState.password.isBlank() ||
            currentState.avatar.isBlank()
        ) {
            setState { copy(error = "Please fill in all fields") }
            return
        }

        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                authApi.registerUser(
                    RegisterUserRequestDto(
                        name = currentState.name,
                        email = currentState.email,
                        password = currentState.password,
                        avatar = currentState.avatar
                    )
                )
                val response = authApi.login(
                    LoginRequestDto(
                        email = currentState.email,
                        password = currentState.password
                    )
                )
                tokenManager.saveTokens(
                    access = response.accessToken,
                    refresh = response.refreshToken
                )
                setState { copy(isLoading = false) }
                setEffect(RegisterEffect.NavigateToMain)
            } catch (e: Exception) {
                val message = e.message ?: "Registration failed"
                setState { copy(isLoading = false, error = message) }
                setEffect(RegisterEffect.ShowError(message))
            }
        }
    }
}
