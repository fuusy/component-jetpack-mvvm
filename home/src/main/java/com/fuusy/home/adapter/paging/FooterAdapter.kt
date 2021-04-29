package com.fuusy.home.adapter.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuusy.home.databinding.PagingFooterItemBinding

private const val TAG = "FooterAdapter"
class FooterAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.FooterViewHolder>() {

    class FooterViewHolder(binding: PagingFooterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var pagingBinding = binding
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> holder.pagingBinding.progressBar.isVisible
            is LoadState.Error -> holder.pagingBinding.btRetry.isVisible
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        val binding =
            PagingFooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.btRetry.setOnClickListener {
            Log.d(TAG, "onCreateViewHolder: retry")
            retry()
        }
        return FooterViewHolder(binding)

    }
}