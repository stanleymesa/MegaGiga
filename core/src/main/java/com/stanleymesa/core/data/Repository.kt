package com.stanleymesa.core.data

import androidx.paging.PagingData
import com.stanleymesa.core.data.source.local.LocalDataSource
import com.stanleymesa.core.data.source.remote.RemoteDataSource
import com.stanleymesa.core.domain.body.CreateProductBody
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.body.UpdateProductBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.domain.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val coroutineScope: CoroutineScope,
): IRepository {

    override fun login(loginBody: LoginBody): Flow<Resource<Login>> = flow {
        emitAll(remoteDataSource.login(loginBody))
    }.flowOn(Dispatchers.IO)

    override fun register(registerBody: RegisterBody): Flow<Resource<Register>> = flow {
        emitAll(remoteDataSource.register(registerBody))
    }.flowOn(Dispatchers.IO)

    override fun getProduct(token: String): Flow<PagingData<Product>> =
        remoteDataSource.getProduct(token)

    override fun getSupplier(token: String): Flow<PagingData<Supplier>> =
        remoteDataSource.getSupplier(token)

    override fun getToken(): Flow<String> =
        localDataSource.getToken()

    override fun saveToken(token: String) {
        coroutineScope.launch {
            localDataSource.saveToken(token)
        }
    }

    override fun createProduct(
        token: String,
        createProductBody: CreateProductBody,
    ): Flow<Resource<String>> = flow {
        emitAll(remoteDataSource.createProduct(token, createProductBody))
    }.flowOn(Dispatchers.IO)

    override fun updateProduct(
        token: String,
        productId: Int,
        updateProductBody: UpdateProductBody,
    ): Flow<Resource<String>> = flow {
        emitAll(remoteDataSource.updateProduct(token, productId, updateProductBody))
    }.flowOn(Dispatchers.IO)

    override fun deleteProduct(token: String, productId: Int): Flow<Resource<String>> = flow {
        emitAll(remoteDataSource.deleteProduct(token, productId))
    }.flowOn(Dispatchers.IO)

    override fun getProductById(token: String, productId: Int): Flow<Resource<Product>> = flow {
        emitAll(remoteDataSource.getProductById(token, productId))
    }.flowOn(Dispatchers.IO)

}