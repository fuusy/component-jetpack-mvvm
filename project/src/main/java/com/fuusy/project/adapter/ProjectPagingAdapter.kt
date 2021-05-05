package com.fuusy.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.fuusy.common.support.Constants
import com.fuusy.project.bean.ProjectContent
import com.fuusy.project.databinding.ItemProjectContentBinding

class ProjectPagingAdapter() :
    PagingDataAdapter<ProjectContent, ProjectPagingAdapter.ProjectVH>(diffCallback) {

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

    class ProjectVH(private val binding: ItemProjectContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ProjectContent) {
            binding.run {
                Glide.with(this.root)
                    .load(data.envelopePic)
                    .into(ivProjectIcon)
                tvProjectName.text = data.title
                tvSubName.text = data.desc

            }
        }
    }

    override fun onBindViewHolder(holder: ProjectVH, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_WEBVIEW)
                .withString(Constants.KEY_WEBVIEW_PATH, item?.link)
                .withString(Constants.KEY_WEBVIEW_TITLE,item?.title)
                .navigation()
        }
        item?.let { holder.bindData(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectVH {
        return ProjectVH(
            ItemProjectContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }
}