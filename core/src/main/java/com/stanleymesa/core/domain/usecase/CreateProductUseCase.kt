package com.stanleymesa.core.domain.usecase

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.CreateProductBody
import kotlinx.coroutines.flow.Flow

interface CreateProductUseCase {

    fun createProduct(token: String, createProductBody: CreateProductBody): Flow<Resource<String>>

    fun getToken(): Flow<String>

}