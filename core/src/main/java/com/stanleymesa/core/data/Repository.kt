package com.stanleymesa.core.data

import com.stanleymesa.core.data.source.remote.RemoteDataSource
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): IRepository {

    override fun login(loginBody: LoginBody): Flow<Resource<Login>> = flow {
        emitAll(remoteDataSource.login(loginBody))
    }.flowOn(Dispatchers.IO)

    override fun register(registerBody: RegisterBody): Flow<Resource<Register>> = flow {
        emitAll(remoteDataSource.register(registerBody))
    }.flowOn(Dispatchers.IO)

}