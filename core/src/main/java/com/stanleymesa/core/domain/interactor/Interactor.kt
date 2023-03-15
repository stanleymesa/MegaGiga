package com.stanleymesa.core.domain.interactor

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.CreateProductBody
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.body.UpdateProductBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.domain.repository.IRepository
import com.stanleymesa.core.domain.usecase.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Interactor @Inject constructor(private val repository: IRepository) : LoginUseCase,
    RegisterUseCase, ProductUseCase, SupplierUseCase, CreateProductUseCase, UpdateProductUseCase {

    override fun login(loginBody: LoginBody): Flow<Resource<Login>> =
        repository.login(loginBody)

    override fun saveToken(token: String) =
        repository.saveToken(token)

    override fun register(registerBody: RegisterBody): Flow<Resource<Register>> =
        repository.register(registerBody)

    override fun getProduct(token: String): Flow<PagingData<Product>> =
        repository.getProduct(token)

    override fun getSupplier(token: String): Flow<PagingData<Supplier>> =
        repository.getSupplier(token)

    override fun createProduct(
        token: String,
        createProductBody: CreateProductBody,
    ): Flow<Resource<String>> =
        repository.createProduct(token, createProductBody)

    override fun updateProduct(
        token: String,
        productId: Int,
        updateProductBody: UpdateProductBody,
    ): Flow<Resource<String>> =
        repository.updateProduct(token, productId, updateProductBody)

    override fun getToken(): Flow<String> =
        repository.getToken()

}