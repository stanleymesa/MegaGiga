package com.stanleymesa.core.domain.repository

import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Register
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun login(loginBody: LoginBody): Flow<Resource<Login>>

    fun register(registerBody: RegisterBody): Flow<Resource<Register>>

}