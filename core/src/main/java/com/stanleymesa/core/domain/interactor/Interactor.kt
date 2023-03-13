package com.stanleymesa.core.domain.interactor

import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.repository.IRepository
import com.stanleymesa.core.domain.usecase.LoginUseCase
import com.stanleymesa.core.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Interactor @Inject constructor(private val repository: IRepository): LoginUseCase, RegisterUseCase {

    override fun login(loginBody: LoginBody): Flow<Resource<Login>> =
        repository.login(loginBody)

    override fun register(registerBody: RegisterBody): Flow<Resource<Register>> =
        repository.register(registerBody)

}