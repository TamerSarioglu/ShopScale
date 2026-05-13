package com.shopscale.feature.register.domain.repository

import com.shopscale.feature.register.domain.model.RegisterUser
import com.shopscale.feature.register.domain.model.RegisteredUser

interface RegisterRepository {
    suspend fun registerUser(user: RegisterUser): Result<RegisteredUser>
    suspend fun loginRegisteredUser(email: String, password: String): Result<Unit>
}
