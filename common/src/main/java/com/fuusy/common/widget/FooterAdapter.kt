package com.fuusy.common.widget

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuusy.common.databinding.PagingFooterItemBinding


private const val TAG = "FooterAdapter"

class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterAdapter.FooterViewHolder>() {

    class FooterViewHolder(binding: PagingFooterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var pagingBinding = binding
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        Log.d(TAG, "onBindViewHolder: $loadState ")
        when (loadState) {
            is LoadState.Loading -> {
                holder.pagingBinding.progressBar.visibility = View.VISIBLE
                holder.pagingBinding.btRetry.visibility = View.GONE
            }
            is LoadState.Error -> {
                holder.pagingBinding.progressBar.visibility = View.GONE
                holder.pagingBinding.btRetry.visibility = View.VISIBLE
            }

            is LoadState.NotLoading -> {
                holder.pagingBinding.progressBar.visibility = View.GONE
                holder.pagingBinding.btRetry.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {

        val binding =
            PagingFooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.btRetry.setOnClickListener {
            retry()
        }
        return FooterViewHolder(binding)

    }
}