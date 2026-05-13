package com.shopscale.feature.auth.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.feature.auth.domain.model.LoginCredentials
import com.shopscale.feature.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
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
            loginUseCase(
                LoginCredentials(
                    email = currentState.email,
                    password = currentState.password
                )
            ).onSuccess {
                setState { copy(isLoading = false) }
                setEffect(LoginEffect.NavigateToMain)
            }.onFailure { error ->
                val message = error.message ?: "Login failed"
                setState { copy(isLoading = false, error = message) }
                setEffect(LoginEffect.ShowError(message))
            }
        }
    }
}
