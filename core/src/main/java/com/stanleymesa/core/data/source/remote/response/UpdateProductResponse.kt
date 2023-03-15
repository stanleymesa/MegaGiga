package com.stanleymesa.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateProductResponse(

	@field:SerializedName("data")
	val data: ProductItem?,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

