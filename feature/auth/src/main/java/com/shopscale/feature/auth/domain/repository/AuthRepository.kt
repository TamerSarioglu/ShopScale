package com.shopscale.feature.auth.domain.repository

import com.shopscale.feature.auth.domain.model.LoginCredentials

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): Result<Unit>
}
