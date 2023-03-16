package com.stanleymesa.core.domain.interactor

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.*
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
    RegisterUseCase, ProductUseCase, SupplierUseCase, CreateProductUseCase, UpdateProductUseCase,
    CreateSupplierUseCase, UpdateSupplierUseCase {

    override fun login(loginBody: LoginBody): Flow<Resource<Login>> =
        repository.login(loginBody)

    override fun saveToken(token: String) =
        repository.saveToken(token)

    override fun register(registerBody: RegisterBody): Flow<Resource<Register>> =
        repository.register(registerBody)

    override fun getProduct(token: String): Flow<PagingData<Product>> =
        repository.getProduct(token)

    override fun deleteProduct(token: String, productId: Int): Flow<Resource<String>> =
        repository.deleteProduct(token, productId)

    override fun getSupplier(token: String): Flow<PagingData<Supplier>> =
        repository.getSupplier(token)

    override fun deleteSupplier(token: String, supplierId: Int): Flow<Resource<String>> =
        repository.deleteSupplier(token, supplierId)

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

    override fun getProductById(token: String, productId: Int): Flow<Resource<Product>> =
        repository.getProductById(token, productId)

    override fun createSupplier(token: String, supplierBody: SupplierBody): Flow<Resource<String>> =
        repository.createSupplier(token, supplierBody)

    override fun updateSupplier(
        token: String,
        supplierId: Int,
        supplierBody: SupplierBody,
    ): Flow<Resource<String>> =
        repository.updateSupplier(token, supplierId, supplierBody)

    override fun getSupplierById(token: String, supplierId: Int): Flow<Resource<Supplier>> =
        repository.getSupplierById(token, supplierId)

    override fun getToken(): Flow<String> =
        repository.getToken()

}