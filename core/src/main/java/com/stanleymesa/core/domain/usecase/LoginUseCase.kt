package com.stanleymesa.core.domain.usecase

import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.model.Login
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun login(loginBody: LoginBody): Flow<Resource<Login>>

}