package com.shopscale.feature.productdetail.data.remote.api

import com.shopscale.feature.productdetail.data.remote.dto.ProductDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailApi {
    @GET("products/{id}")
    suspend fun getProductDetail(
        @Path("id") productId: Int
    ): ProductDetailDto
}