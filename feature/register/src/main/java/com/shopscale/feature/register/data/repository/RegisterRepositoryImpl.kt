package com.shopscale.feature.register.data.repository

import com.shopscale.core.network.TokenManager
import com.shopscale.core.network.api.ShopScaleAuthApi
import com.shopscale.core.network.model.dto.LoginRequestDto
import com.shopscale.feature.register.data.mapper.toDomain
import com.shopscale.feature.register.data.mapper.toDto
import com.shopscale.feature.register.data.remote.api.RegisterApi
import com.shopscale.feature.register.domain.model.RegisterUser
import com.shopscale.feature.register.domain.model.RegisteredUser
import com.shopscale.feature.register.domain.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerApi: RegisterApi,
    private val authApi: ShopScaleAuthApi,
    private val tokenManager: TokenManager
) : RegisterRepository {

    override suspend fun registerUser(user: RegisterUser): Result<RegisteredUser> {
        return withContext(Dispatchers.IO) {
            try {
                val response = registerApi.registerUser(user.toDto()).toDomain()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun loginRegisteredUser(email: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authApi.login(
                    LoginRequestDto(
                        email = email,
                        password = password
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
