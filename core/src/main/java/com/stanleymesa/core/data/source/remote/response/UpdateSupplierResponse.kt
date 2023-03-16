package com.stanleymesa.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateSupplierResponse(

	@field:SerializedName("data")
	val data: SupplierItem?,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

