package com.stanleymesa.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stanleymesa.core.databinding.ItemRowSupplierBinding
import com.stanleymesa.core.domain.model.Supplier

class SupplierAdapter(private val onSupplierClickCallback: OnSupplierClickCallback) :
    PagingDataAdapter<Supplier, SupplierAdapter.SupplierViewHolder>(DiffCallbackSupplier) {
    inner class SupplierViewHolder(var binding: ItemRowSupplierBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val binding =
            ItemRowSupplierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SupplierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        getItem(position)?.let { supplier ->
            holder.binding.apply {
                tvId.text = supplier.id.toString()
                tvSupplierName.text = supplier.namaSupplier
                tvSupplierHome.text = supplier.alamat
                tvSupplierPhone.text = supplier.noTelp

                ivEdit.setOnClickListener {
                    onSupplierClickCallback.onEditClicked(supplier.id)
                }

                ivRemove.setOnClickListener {
                    onSupplierClickCallback.onRemoveClicked(supplier.id)
                }
            }
        }
    }

    interface OnSupplierClickCallback {
        fun onSupplierClicked(id: Int)
        fun onEditClicked(id: Int)
        fun onRemoveClicked(id: Int)
    }

}