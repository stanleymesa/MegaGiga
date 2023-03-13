package com.stanleymesa.core.domain.usecase

import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.domain.model.Register
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun register(registerBody: RegisterBody): Flow<Resource<Register>>

}