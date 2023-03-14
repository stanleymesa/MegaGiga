package com.stanleymesa.core.ui

import androidx.recyclerview.widget.DiffUtil
import com.stanleymesa.core.domain.model.Supplier

object DiffCallbackSupplier : DiffUtil.ItemCallback<Supplier>() {
    override fun areItemsTheSame(oldItem: Supplier, newItem: Supplier): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Supplier, newItem: Supplier): Boolean {
        return oldItem == newItem
    }
}