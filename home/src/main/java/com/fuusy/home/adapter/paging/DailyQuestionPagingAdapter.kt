package com.fuusy.home.adapter.paging

import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.paging.BasePagingAdapter
import com.fuusy.common.support.Constants
import com.fuusy.common.support.Constants.Companion.KEY_WEBVIEW_PATH
import com.fuusy.common.support.Constants.Companion.KEY_WEBVIEW_TITLE
import com.fuusy.home.R
import com.fuusy.home.bean.DailyQuestionData

class DailyQuestionPagingAdapter :BasePagingAdapter<DailyQuestionData> (diffCallback){

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

    override fun getItemLayout(position: Int): Int =  R.layout.item_rv_article

    override fun bindData(helper: ItemHelper, data: DailyQuestionData?) {
        helper.setText(R.id.tv_article_title, data?.title)
        helper.setText(R.id.bt_health_info_type, data?.superChapterName)
        helper.setText(R.id.tv_article_author, data?.author)
        helper.setText(R.id.tv_home_info_time, data?.niceShareDate)
    }

    override fun onItemClick(data: DailyQuestionData?) {
        ARouter.getInstance()
            .build(Constants.PATH_WEBVIEW)
            .withString(KEY_WEBVIEW_PATH, data?.link)
            .withString(KEY_WEBVIEW_TITLE,data?.title)
            .navigation()
    }
}