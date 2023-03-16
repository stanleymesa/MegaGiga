package com.stanleymesa.core.domain.repository

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.*
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

    fun createProduct(token: String, createProductBody: CreateProductBody): Flow<Resource<String>>

    fun updateProduct(token: String, productId: Int, updateProductBody: UpdateProductBody): Flow<Resource<String>>

    fun deleteProduct(token: String, productId: Int): Flow<Resource<String>>

    fun getProductById(token: String, productId: Int): Flow<Resource<Product>>

    fun createSupplier(token: String, supplierBody: SupplierBody): Flow<Resource<String>>

    fun updateSupplier(token: String, supplierId: Int, supplierBody: SupplierBody): Flow<Resource<String>>

    fun getSupplierById(token: String, supplierId: Int): Flow<Resource<Supplier>>

    fun deleteSupplier(token: String, supplierId: Int): Flow<Resource<String>>

}