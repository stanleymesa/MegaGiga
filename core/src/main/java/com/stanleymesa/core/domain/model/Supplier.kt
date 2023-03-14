package com.stanleymesa.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Supplier(

    val namaSupplier: String,

    val id: Int,

    val noTelp: String,

    val alamat: String

) : Parcelable
