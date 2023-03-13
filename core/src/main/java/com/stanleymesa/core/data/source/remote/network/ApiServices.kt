package com.stanleymesa.core.data.source.remote.network

import com.stanleymesa.core.data.source.remote.response.LoginResponse
import com.stanleymesa.core.data.source.remote.response.RegisterResponse
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
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

}