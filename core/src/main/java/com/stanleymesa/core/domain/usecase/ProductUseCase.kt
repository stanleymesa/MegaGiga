package com.stanleymesa.core.domain.usecase

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.LoginBody
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {

    fun getProduct(token: String): Flow<PagingData<Product>>

    fun getToken(): Flow<String>

}