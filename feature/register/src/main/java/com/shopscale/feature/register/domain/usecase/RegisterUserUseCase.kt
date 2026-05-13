package com.shopscale.feature.register.domain.usecase

import com.shopscale.feature.register.domain.model.RegisterUser
import com.shopscale.feature.register.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(user: RegisterUser): Result<Unit> {
        return repository.registerUser(user).fold(
            onSuccess = {
                repository.loginRegisteredUser(
                    email = user.email,
                    password = user.password
                )
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}
