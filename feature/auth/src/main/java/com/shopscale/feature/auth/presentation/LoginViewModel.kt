package com.shopscale.feature.auth.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.core.network.TokenManager
import com.shopscale.core.network.api.ShopScaleAuthApi
import com.shopscale.core.network.model.dto.LoginRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authApi: ShopScaleAuthApi,
    private val tokenManager: TokenManager
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>() {

    override fun createInitialState() = LoginState()

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> setState { copy(email = event.email, error = null) }
            is LoginEvent.OnPasswordChanged -> setState { copy(password = event.password, error = null) }
            is LoginEvent.OnLoginClicked -> login()
        }
    }

    private fun login() {
        val currentState = state.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            setState { copy(error = "Please fill in all fields") }
            return
        }

        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
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
                setEffect(LoginEffect.NavigateToMain)
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message ?: "Login failed") }
                setEffect(LoginEffect.ShowError(e.message ?: "Login failed"))
            }
        }
    }
}
