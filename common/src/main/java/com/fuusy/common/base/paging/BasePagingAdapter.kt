package com.fuusy.common.base.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagingAdapter<t : Any, VH : RecyclerView.ViewHolder>(var diffCallback: DiffUtil.ItemCallback<t>) :
    PagingDataAdapter<t, VH>(diffCallback) {

    companion object {

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("Not yet implemented")
    }


}