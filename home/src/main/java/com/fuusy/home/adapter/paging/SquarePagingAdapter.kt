package com.fuusy.home.adapter.paging

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.paging.BasePagingAdapter
import com.fuusy.common.support.Constants
import com.fuusy.home.R
import com.fuusy.home.bean.SquareData

private const val TAG = "SquarePagingAdapter"

class SquarePagingAdapter : BasePagingAdapter<SquareData>(differCallback) {

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

    override fun getItemLayout(position: Int): Int = R.layout.item_rv_article

    override fun onItemClick(data: SquareData?) {
        ARouter.getInstance()
            .build(Constants.PATH_WEBVIEW)
            .withString(Constants.KEY_WEBVIEW_PATH, data?.link)
            .withString(Constants.KEY_WEBVIEW_TITLE, data?.title)
            .navigation()
    }

    override fun bindData(helper: ItemHelper, data: SquareData?) {
        helper.setText(R.id.tv_article_title, data?.title)
        helper.setText(R.id.bt_health_info_type, data?.superChapterName)
        helper.setText(R.id.tv_home_info_time, data?.niceDate)
        helper.setText(R.id.tv_article_author, data?.shareUser)

    }
}

