package com.stanleymesa.core.domain.body

import com.google.gson.annotations.SerializedName

data class UpdateProductBody(

    @field:SerializedName("namaBarang")
    val namaBarang: String,

    @field:SerializedName("harga")
    val harga: Double,

    @field:SerializedName("stok")
    val stok: Int,

    @field:SerializedName("supplier")
    val supplier: SupplierBody,

)