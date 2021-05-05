package com.fuusy.home.adapter.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.support.Constants
import com.fuusy.common.support.Constants.Companion.KEY_WEBVIEW_PATH
import com.fuusy.common.support.Constants.Companion.KEY_WEBVIEW_TITLE
import com.fuusy.home.bean.DailyQuestionData
import com.fuusy.home.databinding.ItemRvArticleBinding

class DailyQuestionPagingAdapter :
    PagingDataAdapter<DailyQuestionData, DailyQuestionPagingAdapter.DailyVH>(diffCallback) {

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<DailyQuestionData>() {
            override fun areItemsTheSame(
                oldItem: DailyQuestionData,
                newItem: DailyQuestionData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DailyQuestionData,
                newItem: DailyQuestionData
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class DailyVH(val binding: ItemRvArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: DailyQuestionData) {
            binding.tvArticleTitle.text = data.title
            binding.btHealthInfoType.text = data.superChapterName
            binding.tvArticleAuthor.text = data.author
            binding.tvHomeInfoTime.text = data.niceShareDate
        }
    }

    override fun onBindViewHolder(holder: DailyVH, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_WEBVIEW)
                .withString(KEY_WEBVIEW_PATH, item?.link)
                .withString(KEY_WEBVIEW_TITLE,item?.title)
                .navigation()
        }
        item?.let { holder.bindData(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyVH {
        val dailyVH = DailyVH(
            ItemRvArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        dailyVH.itemView.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_WEBVIEW)
                .withString(KEY_WEBVIEW_PATH, getItem(dailyVH.layoutPosition)?.link)
                .withString(KEY_WEBVIEW_TITLE,getItem(dailyVH.layoutPosition)?.title)
                .navigation()
        }
        return dailyVH

    }
}