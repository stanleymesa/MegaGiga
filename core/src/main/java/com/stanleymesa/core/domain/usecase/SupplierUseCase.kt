package com.stanleymesa.core.domain.usecase

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierUseCase {

    fun getSupplier(token: String): Flow<PagingData<Supplier>>

    fun deleteSupplier(token: String, supplierId: Int): Flow<Resource<String>>

    fun getToken(): Flow<String>

}