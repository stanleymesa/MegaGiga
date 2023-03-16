package com.stanleymesa.core.data.source.remote.network

import com.stanleymesa.core.data.source.remote.response.*
import com.stanleymesa.core.domain.body.CreateProductBody
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.body.UpdateProductBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @POST("auth/login")
    @Headers("Accept: application/json")
    suspend fun login(
        @Body loginBody: LoginBody
    ): Response<LoginResponse>

    @POST("auth/register")
    @Headers("Accept: application/json")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): Response<RegisterResponse>

    @GET("barang/find-all")
    @Headers("Accept: application/json")
    suspend fun getProduct(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<ProductResponse>

    @GET("supplier/find-all")
    @Headers("Accept: application/json")
    suspend fun getSupplier(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<SupplierResponse>

    @POST("barang/create")
    @Headers("Accept: application/json")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body createProductBody: CreateProductBody
    ): Response<CreateProductResponse>

    @PUT("barang/update/{id}")
    @Headers("Accept: application/json")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") productId: Int,
        @Body updateProductBody: UpdateProductBody
    ): Response<UpdateProductResponse>

    @DELETE("barang/delete/{id}")
    @Headers("Accept: application/json")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") productId: Int,
    ): Response<DeleteProductResponse>

    @GET("barang/find-by-id/{id}")
    @Headers("Accept: application/json")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("id") productId: Int,
    ): Response<ProductByIdResponse>

}