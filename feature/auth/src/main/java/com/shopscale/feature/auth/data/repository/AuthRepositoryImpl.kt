package com.shopscale.feature.auth.data.repository

import com.shopscale.core.network.TokenManager
import com.shopscale.core.network.api.ShopScaleAuthApi
import com.shopscale.core.network.model.dto.LoginRequestDto
import com.shopscale.feature.auth.domain.model.LoginCredentials
import com.shopscale.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: ShopScaleAuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(credentials: LoginCredentials): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authApi.login(
                    LoginRequestDto(
                        email = credentials.email,
                        password = credentials.password
                    )
                )
                tokenManager.saveTokens(
                    access = response.accessToken,
                    refresh = response.refreshToken
                )
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
