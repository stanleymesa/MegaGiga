package com.stanleymesa.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("data")
	val data: List<ProductItem> = arrayListOf(),

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("status")
	val status: String
)

data class SupplierItem(

	@field:SerializedName("namaSupplier")
	val namaSupplier: String?,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("noTelp")
	val noTelp: String?,

	@field:SerializedName("alamat")
	val alamat: String?
)

data class ProductItem(

	@field:SerializedName("harga")
	val harga: Double?,

	@field:SerializedName("supplier")
	val supplier: SupplierItem?,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("namaBarang")
	val namaBarang: String?,

	@field:SerializedName("stok")
	val stok: Int?
)
