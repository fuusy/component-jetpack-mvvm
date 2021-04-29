package com.fuusy.common.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun bindView(data: T) {

    }

    open fun bindView(data: T, position: Int) {

    }
}