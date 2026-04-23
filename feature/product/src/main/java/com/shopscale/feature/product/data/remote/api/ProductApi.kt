package com.shopscale.feature.product.data.remote.api

import com.shopscale.feature.product.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun fetchProducts(): List<ProductDto>
}
