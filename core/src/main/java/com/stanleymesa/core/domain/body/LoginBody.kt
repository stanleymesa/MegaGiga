package com.stanleymesa.core.domain.body

import com.google.gson.annotations.SerializedName

data class LoginBody(

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("password")
    val password: String

)