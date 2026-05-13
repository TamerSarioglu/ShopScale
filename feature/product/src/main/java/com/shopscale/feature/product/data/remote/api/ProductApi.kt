package com.shopscale.feature.product.data.remote.api

import com.shopscale.feature.product.data.remote.dto.CategoryDto
import com.shopscale.feature.product.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun fetchProducts(
        @Query("title") title: String? = null,
        @Query("price") price: Int? = null,
        @Query("price_min") priceMin: Int? = null,
        @Query("price_max") priceMax: Int? = null,
        @Query("categoryId") categoryId: Int? = null,
        @Query("categorySlug") categorySlug: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<ProductDto>

    @GET("categories")
    suspend fun fetchCategories(): List<CategoryDto>

    @GET("categories/{id}/products")
    suspend fun fetchProductsByCategory(
        @Path("id") categoryId: Int,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<ProductDto>
}
