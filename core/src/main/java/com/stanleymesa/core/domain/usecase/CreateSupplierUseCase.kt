package com.stanleymesa.core.domain.usecase

import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.SupplierBody
import kotlinx.coroutines.flow.Flow

interface CreateSupplierUseCase {

    fun createSupplier(token: String, supplierBody: SupplierBody): Flow<Resource<String>>

    fun getToken(): Flow<String>

}