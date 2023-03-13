package com.stanleymesa.core.data.source.remote

import android.util.Log
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.data.source.remote.network.ApiServices
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.utlis.DataMapper
import com.stanleymesa.core.utlis.LOGIN_SUCCESS
import com.stanleymesa.core.utlis.REGISTER_SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices,
    private val coroutineScope: CoroutineScope
) {

    fun login(loginBody: LoginBody): Flow<Resource<Login>> = flow {
        emit(Resource.Loading())
        try {
            val request = apiServices.login(loginBody)

            if (request.isSuccessful) {
                request.body()?.let { response ->
                    if (response.data != null && response.message == LOGIN_SUCCESS) {
                        val data = DataMapper.mapLoginResponseToLogin(response)
                        data?.let { emit(Resource.Success(it)) }
                    } else {
                        emit(Resource.Error(message = response.message))
                    }
                }
            } else {
                emit(Resource.Error(message = "Something went wrong, can't login"))
            }

        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.message ?: "Something went wrong, can't login"))
        }
    }

    fun register(registerBody: RegisterBody): Flow<Resource<Register>> = flow {
        emit(Resource.Loading())
        try {
            val request = apiServices.register(registerBody)

            if (request.isSuccessful) {
                request.body()?.let { response ->
                    if (response.data != null && response.message == REGISTER_SUCCESS) {
                        val data = DataMapper.mapRegisterResponseToRegister(response)
                        data?.let { emit(Resource.Success(it)) }
                    } else {
                        emit(Resource.Error(message = "Username is already registered"))
                    }
                }
            } else {
                emit(Resource.Error(message = "Something went wrong, can't register"))
            }

        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.message ?: "Something went wrong, can't register"))
        }
    }

}