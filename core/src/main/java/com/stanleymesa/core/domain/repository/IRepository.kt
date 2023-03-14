package com.stanleymesa.core.domain.repository

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun login(loginBody: LoginBody): Flow<Resource<Login>>

    fun register(registerBody: RegisterBody): Flow<Resource<Register>>

    fun getProduct(token: String): Flow<PagingData<Product>>

    fun getSupplier(token: String): Flow<PagingData<Supplier>>

    fun getToken(): Flow<String>

    fun saveToken(token: String)

}