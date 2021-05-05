package com.fuusy.home.adapter.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.support.Constants

import com.fuusy.home.bean.SquareData
import com.fuusy.home.databinding.ItemRvArticleBinding

private const val TAG = "SquarePagingAdapter"

class SquarePagingAdapter : PagingDataAdapter<SquareData, SquareVH>(differCallback) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<SquareData>() {
            override fun areItemsTheSame(oldItem: SquareData, newItem: SquareData): Boolean {
                Log.d(TAG, "areItemsTheSame: ")
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SquareData, newItem: SquareData): Boolean {
                Log.d(TAG, "areContentsTheSame: ")
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: SquareVH, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareVH {
        val createVH = SquareVH.createVH(parent)
        createVH.itemView.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_WEBVIEW)
                .withString(Constants.KEY_WEBVIEW_PATH, getItem(createVH.layoutPosition)?.link)
                .withString(Constants.KEY_WEBVIEW_TITLE,getItem(createVH.layoutPosition)?.title)
                .navigation()
        }
        return createVH
    }
}

class SquareVH(private val binding: ItemRvArticleBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun createVH(parent: ViewGroup): SquareVH {
            return SquareVH(
                ItemRvArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(data: SquareData) {
        binding.tvArticleTitle.text = data.title
        binding.btHealthInfoType.text = data.superChapterName
        binding.tvHomeInfoTime.text = data.niceDate
        binding.tvArticleAuthor.text = data.shareUser
    }

}