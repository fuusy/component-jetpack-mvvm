package com.fuusy.common.widget

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuusy.common.databinding.PagingFooterItemBinding
import java.lang.Error


private const val TAG = "FooterAdapter"

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：Paging 上拉尾部Adapter
 */
class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterAdapter.FooterViewHolder>() {

    class FooterViewHolder(binding: PagingFooterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var pagingBinding = binding
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        Log.d(TAG, "onBindViewHolder: $loadState ")

        holder.pagingBinding.run {
            progressBar.isVisible = loadState is LoadState.Loading
            btRetry.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {

        val binding =
            PagingFooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //加载失败时，点击重新请求
        binding.btRetry.setOnClickListener {
            retry()
        }
        return FooterViewHolder(binding)

    }
}