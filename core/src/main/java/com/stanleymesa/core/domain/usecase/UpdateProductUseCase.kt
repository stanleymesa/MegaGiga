package com.stanleymesa.core.domain.usecase

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.CreateProductBody
import com.stanleymesa.core.domain.body.UpdateProductBody
import kotlinx.coroutines.flow.Flow

interface UpdateProductUseCase {

    fun updateProduct(token: String, productId: Int, updateProductBody: UpdateProductBody): Flow<Resource<String>>

    fun getToken(): Flow<String>

}