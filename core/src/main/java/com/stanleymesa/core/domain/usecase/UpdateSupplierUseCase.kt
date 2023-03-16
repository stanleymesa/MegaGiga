package com.stanleymesa.core.domain.usecase

import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.SupplierBody
import com.stanleymesa.core.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

interface UpdateSupplierUseCase {

    fun updateSupplier(token: String, supplierId: Int, supplierBody: SupplierBody): Flow<Resource<String>>

    fun getSupplierById(token: String, supplierId: Int): Flow<Resource<Supplier>>

    fun getToken(): Flow<String>

}