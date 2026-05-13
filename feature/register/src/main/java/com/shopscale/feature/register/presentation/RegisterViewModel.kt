package com.shopscale.feature.register.presentation

import androidx.lifecycle.viewModelScope
import com.shopscale.core.common.mvi.BaseViewModel
import com.shopscale.feature.register.domain.model.RegisterUser
import com.shopscale.feature.register.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
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
            registerUserUseCase(
                RegisterUser(
                    name = currentState.name,
                    email = currentState.email,
                    password = currentState.password,
                    avatar = currentState.avatar
                )
            ).onSuccess {
                setState { copy(isLoading = false) }
                setEffect(RegisterEffect.NavigateToMain)
            }.onFailure { error ->
                val message = error.message ?: "Registration failed"
                setState { copy(isLoading = false, error = message) }
                setEffect(RegisterEffect.ShowError(message))
            }
        }
    }
}
