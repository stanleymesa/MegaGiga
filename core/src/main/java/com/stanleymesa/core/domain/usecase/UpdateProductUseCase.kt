package com.stanleymesa.core.domain.usecase

import androidx.paging.PagingData
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.CreateProductBody
import com.stanleymesa.core.domain.body.UpdateProductBody
import com.stanleymesa.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface UpdateProductUseCase {

    fun updateProduct(token: String, productId: Int, updateProductBody: UpdateProductBody): Flow<Resource<String>>

    fun getProductById(token: String, productId: Int): Flow<Resource<Product>>

    fun getToken(): Flow<String>

}