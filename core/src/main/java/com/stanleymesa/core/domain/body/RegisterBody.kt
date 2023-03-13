package com.stanleymesa.core.domain.body

import com.google.gson.annotations.SerializedName

data class RegisterBody(

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("profileName")
    val profileName: String,

    @field:SerializedName("password")
    val password: String

)