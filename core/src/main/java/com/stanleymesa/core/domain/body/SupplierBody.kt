package com.stanleymesa.core.domain.body

import com.google.gson.annotations.SerializedName

data class SupplierBody(

    @field:SerializedName("namaSupplier")
    val namaSupplier: String?,

    @field:SerializedName("noTelp")
    val noTelp: String?,

    @field:SerializedName("alamat")
    val alamat: String?

)