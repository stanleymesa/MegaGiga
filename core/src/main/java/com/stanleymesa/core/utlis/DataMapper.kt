package com.stanleymesa.core.utlis

import com.stanleymesa.core.data.source.remote.response.LoginResponse
import com.stanleymesa.core.data.source.remote.response.RegisterResponse
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Register

object DataMapper {
    fun mapLoginResponseToLogin(loginResponse: LoginResponse): Login? =
        loginResponse.data?.let { data ->
            Login(
                id = data.id,
                username = data.username,
                profileName = data.profileName,
                token = data.token,
                message = loginResponse.message,
                status = loginResponse.status
            )
        }

    fun mapRegisterResponseToRegister(registerResponse: RegisterResponse): Register? =
        registerResponse.data?.let { data ->
            Register(
                id = data.id,
                username = data.username,
                profileName = data.profileName,
                message = registerResponse.message,
                status = registerResponse.status
            )
        }
}