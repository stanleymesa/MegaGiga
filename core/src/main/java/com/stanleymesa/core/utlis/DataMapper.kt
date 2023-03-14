package com.stanleymesa.core.utlis

import com.stanleymesa.core.data.source.remote.response.LoginResponse
import com.stanleymesa.core.data.source.remote.response.ProductItem
import com.stanleymesa.core.data.source.remote.response.RegisterResponse
import com.stanleymesa.core.data.source.remote.response.SupplierItem
import com.stanleymesa.core.domain.model.Login
import com.stanleymesa.core.domain.model.Product
import com.stanleymesa.core.domain.model.Register
import com.stanleymesa.core.domain.model.Supplier

object DataMapper {
    fun mapLoginResponseToLogin(loginResponse: LoginResponse): Login? =
        loginResponse.data?.let { data ->
            Login(
                id = data.id,
                username = data.username,
                profileName = data.profileName.toString(),
                token = data.token,
                message = loginResponse.message,
                status = loginResponse.status
            )
        }

    fun mapRegisterResponseToRegister(registerResponse: RegisterResponse): Register? =
        registerResponse.data?.let { data ->
            Register(
                id = data.id,
                username = data.username,
                profileName = data.profileName.toString(),
                message = registerResponse.message,
                status = registerResponse.status
            )
        }

    fun mapSupplierItemToSupplier(supplierItem: SupplierItem?): Supplier? =
        supplierItem?.let {
            Supplier(
                id = it.id,
                namaSupplier = it.namaSupplier.toString(),
                alamat = it.alamat.toString(),
                noTelp = it.noTelp.toString()
            )
        }

    fun mapProductItemToProduct(productItem: ProductItem): Product =
        Product(
            id = productItem.id,
            harga = productItem.harga ?: 0.0,
            supplier = mapSupplierItemToSupplier(productItem.supplier),
            namaBarang = productItem.namaBarang.toString(),
            stok = productItem.stok ?: 0
        )

    fun mapSupplierNotNullToSupplier(supplierItem: SupplierItem): Supplier =
        Supplier(
            id = supplierItem.id,
            namaSupplier = supplierItem.namaSupplier.toString(),
            alamat = supplierItem.alamat.toString(),
            noTelp = supplierItem.noTelp.toString()
        )
}