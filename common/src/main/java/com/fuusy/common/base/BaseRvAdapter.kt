package com.fuusy.common.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateNormalViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return getNormalItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindNormalViewHolder(holder, position)
    }

    abstract fun onCreateNormalViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    abstract fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun getNormalItemCount(): Int



}