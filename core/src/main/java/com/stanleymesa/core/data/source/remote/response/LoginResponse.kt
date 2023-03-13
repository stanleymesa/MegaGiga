package com.stanleymesa.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("profileName")
	val profileName: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("token")
	val token: String
)
