package com.stanleymesa.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(

    val harga: Double,

    val supplier: Supplier?,

    val id: Int,

    val namaBarang: String,

    val stok: Int

) : Parcelable
