package com.stanleymesa.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stanleymesa.core.databinding.ItemRowProductBinding
import com.stanleymesa.core.domain.model.Product

class ProductAdapter(private val onProductClickCallback: OnProductClickCallback) :
    PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallbackProduct) {
    inner class ProductViewHolder(var binding: ItemRowProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemRowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { product ->
            holder.binding.apply {
                tvId.text = product.id.toString()
                tvProductName.text = product.namaBarang
                tvPrice.text = "Rp ${product.harga.toString()}"
                tvStock.text = product.stok.toString()
                tvSupplierName.text = product.supplier?.namaSupplier.toString()
                tvSupplierHome.text = product.supplier?.alamat.toString()
                tvSupplierPhone.text = product.supplier?.noTelp.toString()
            }
        }
    }

    interface OnProductClickCallback {
        fun onProductClicked(id: Int)
        fun onEditClicked(id: Int)
        fun onRemoveClicked(id: Int)
    }

}