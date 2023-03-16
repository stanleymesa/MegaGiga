package com.stanleymesa.core.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.beust.klaxon.Klaxon
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.data.paging.ProductPagingSource
import com.stanleymesa.core.data.paging.SupplierPagingSource
import com.stanleymesa.core.data.source.remote.network.ApiServices
import com.stanleymesa.core.data.source.remote.response.ProductByIdResponse
import com.stanleymesa.core.domain.body.CreateProductBody
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.body.UpdateProductBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.utlis.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices,
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

    fun getProduct(token: String): Flow<PagingData<Product>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ProductPagingSource(token, apiServices)
            }
        ).flow

    fun getSupplier(token: String): Flow<PagingData<Supplier>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SupplierPagingSource(token, apiServices)
            }
        ).flow

    fun createProduct(token: String, createProductBody: CreateProductBody): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val request = apiServices.createProduct(token, createProductBody)
                if (request.isSuccessful) {
                    request.body()?.let { response ->
                        if (response.status == STATUS_OK && response.data != null)
                            emit(Resource.Success(response.message))
                        else
                            emit(Resource.Error("Something went wrong, can't create product"))
                    }
                } else {
                    if (request.code() == STATUS_UNAUTHORIZED)
                        emit(Resource.Error(request.code().toString()))
                    else
                        emit(Resource.Error("Something went wrong, can't create product"))
                }

            } catch (ex: Exception) {
                emit(Resource.Error(ex.message ?: "Something went wrong, can't create product"))
            }
        }

    fun updateProduct(
        token: String,
        productId: Int,
        updateProductBody: UpdateProductBody,
    ): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val request = apiServices.updateProduct(token, productId, updateProductBody)
                if (request.isSuccessful) {
                    request.body()?.let { response ->
                        if (response.status == STATUS_OK && response.data != null)
                            emit(Resource.Success(response.message))
                        else
                            emit(Resource.Error("Something went wrong, can't update product"))
                    }
                } else {
                    if (request.code() == STATUS_UNAUTHORIZED)
                        emit(Resource.Error(request.code().toString()))
                    else
                        emit(Resource.Error("Something went wrong, can't update product"))
                }

            } catch (ex: Exception) {
                emit(Resource.Error(ex.message ?: "Something went wrong, can't update product"))
            }
        }

    fun deleteProduct(token: String, productId: Int): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            try {
                val request = apiServices.deleteProduct(token, productId)
                if (request.isSuccessful) {
                    request.body()?.let { response ->
                        if (response.status == STATUS_OK && response.message == DELETE_SUCCESS)
                            emit(Resource.Success(response.message))
                        else
                            emit(Resource.Error("Something went wrong, can't delete product"))
                    }
                } else {
                    if (request.code() == STATUS_UNAUTHORIZED)
                        emit(Resource.Error(request.code().toString()))
                    else
                        emit(Resource.Error("Something went wrong, can't delete product"))
                }

            } catch (ex: Exception) {
                emit(Resource.Error(ex.message ?: "Something went wrong, can't delete product"))
            }
        }

    fun getProductById(token: String, productId: Int): Flow<Resource<Product>> =
        flow {
            emit(Resource.Loading())
            try {
                val request = apiServices.getProductById(token, productId)
                if (request.isSuccessful) {
                    request.body()?.let { response ->
                        if (response.status == STATUS_OK && response.message == GET_PRODUCT_BY_ID_SUCCESS && response.data != null) {
                            val data = DataMapper.mapProductItemToProduct(response.data)
                            emit(Resource.Success(data))
                        } else {
                            emit(Resource.Error("Something went wrong, can't find product"))
                        }
                    }
                } else {
                    if (request.code() == STATUS_UNAUTHORIZED)
                        emit(Resource.Error(request.code().toString()))
                    else
                        emit(Resource.Error("Something went wrong, can't find product"))
                }

            } catch (ex: Exception) {
                emit(Resource.Error(ex.message ?: "Something went wrong, can't find product"))
            }
        }

}