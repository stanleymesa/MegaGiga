package com.stanleymesa.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SupplierResponse(

	@field:SerializedName("data")
	val data: List<SupplierItem> = arrayListOf(),

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
