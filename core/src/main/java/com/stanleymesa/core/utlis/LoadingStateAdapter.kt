package com.stanleymesa.core.utlis

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stanleymesa.core.R
import com.stanleymesa.core.databinding.ItemLoadingBinding

class LoadingStateAdapter(
    private val retry: () -> Unit,
    private val onPagingErrorCallback: OnPagingErrorCallback,
) : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    inner class LoadingStateViewHolder(var binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        val context = holder.binding.root.context
        holder.binding.apply {
            btnRetry.setOnClickListener { retry.invoke() }

            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                tvError.text = context.getString(R.string.rto)
                if (loadState.error.message.toString() == "401") {
                    onPagingErrorCallback.onTokenExpired()
                }
            }
        }
    }

    interface OnPagingErrorCallback {
        fun onTokenExpired()
    }

}