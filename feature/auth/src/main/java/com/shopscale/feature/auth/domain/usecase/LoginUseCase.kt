package com.shopscale.feature.auth.domain.usecase

import com.shopscale.feature.auth.domain.model.LoginCredentials
import com.shopscale.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(credentials: LoginCredentials): Result<Unit> {
        return repository.login(credentials)
    }
}
