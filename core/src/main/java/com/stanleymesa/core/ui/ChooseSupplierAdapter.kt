package com.stanleymesa.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stanleymesa.core.databinding.ItemRowSupplierBinding
import com.stanleymesa.core.domain.model.Supplier

class ChooseSupplierAdapter(private val onSupplierClickCallback: OnSupplierClickCallback) :
    PagingDataAdapter<Supplier, ChooseSupplierAdapter.ChooseSupplierViewHolder>(DiffCallbackSupplier) {
    inner class ChooseSupplierViewHolder(var binding: ItemRowSupplierBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseSupplierViewHolder {
        val binding =
            ItemRowSupplierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChooseSupplierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseSupplierViewHolder, position: Int) {
        getItem(position)?.let { supplier ->
            holder.binding.apply {
                ivEdit.isVisible = false
                ivRemove.isVisible = false
                tvId.text = supplier.id.toString()
                tvSupplierName.text = supplier.namaSupplier
                tvSupplierHome.text = supplier.alamat
                tvSupplierPhone.text = supplier.noTelp

                root.setOnClickListener {
                    onSupplierClickCallback.onSupplierClicked(supplier)
                }
            }

        }
    }

    interface OnSupplierClickCallback {
        fun onSupplierClicked(supplier: Supplier)
    }

}