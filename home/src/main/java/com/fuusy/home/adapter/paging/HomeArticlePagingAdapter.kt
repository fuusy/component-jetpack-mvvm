package com.fuusy.home.adapter.paging

import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.paging.BasePagingAdapter
import com.fuusy.common.support.Constants
import com.fuusy.home.bean.ArticleData
import com.fuusy.home.R

class HomeArticlePagingAdapter :
    BasePagingAdapter<ArticleData>(differCallback) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<ArticleData>() {
            override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {

                return oldItem == newItem
            }

        }
    }

    override fun getItemLayout(position: Int): Int = R.layout.item_rv_article

    override fun onItemClick(data: ArticleData?) {
        //跳转到webView
        ARouter.getInstance()
            .build(Constants.PATH_WEBVIEW)
            .withString(Constants.KEY_WEBVIEW_PATH, data?.link)
            .withString(Constants.KEY_WEBVIEW_TITLE, data?.title)
            .navigation()
    }

    override fun bindData(helper: ItemHelper, data: ArticleData?) {
        helper.setText(R.id.tv_article_title, data?.title)
        helper.setText(R.id.bt_health_info_type, data?.superChapterName)
        helper.setText(R.id.tv_home_info_time, data?.niceDate)

        if (data?.author?.isEmpty()!!) {
            helper.setText(R.id.tv_article_author, data?.shareUser)
        } else {
            helper.setText(R.id.tv_article_author, data?.author)
        }
    }
}
