package com.fuusy.common.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuusy.common.databinding.PagingFooterItemBinding


private const val TAG = "FooterAdapter"
class FooterAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.FooterViewHolder>() {

    class FooterViewHolder(binding: PagingFooterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var pagingBinding = binding
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                holder.pagingBinding.progressBar.visibility = View.VISIBLE
                holder.pagingBinding.btRetry.visibility = View.GONE
            }
            is LoadState.Error -> {
                holder.pagingBinding.progressBar.visibility = View.GONE
                holder.pagingBinding.btRetry.visibility = View.VISIBLE
            }
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