package com.stanleymesa.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(

    val id: Int,

    val username: String = "",

    val profileName: String = "",

    val token: String,

    val message: String,

    val status: String

): Parcelable
