package com.fuusy.project.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.paging.BasePagingAdapter
import com.fuusy.common.support.Constants
import com.fuusy.project.R
import com.fuusy.project.bean.ProjectContent

class ProjectPagingAdapter() :
    BasePagingAdapter<ProjectContent>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ProjectContent>() {
            override fun areItemsTheSame(
                oldItem: ProjectContent,
                newItem: ProjectContent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProjectContent,
                newItem: ProjectContent
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun getItemLayout(position: Int): Int = R.layout.item_project_content

    override fun onItemClick(data: ProjectContent?) {
        ARouter.getInstance()
            .build(Constants.PATH_WEBVIEW)
            .withString(Constants.KEY_WEBVIEW_PATH, data?.link)
            .withString(Constants.KEY_WEBVIEW_TITLE, data?.title)
            .navigation()
    }

    override fun bindData(helper: ItemHelper, data: ProjectContent?) {
        helper.setText(R.id.tv_project_name, data?.title)
        helper.setText(R.id.tv_sub_name, data?.desc)
        helper.bindImgGlide(R.id.iv_project_icon, data?.envelopePic!!)
    }
}