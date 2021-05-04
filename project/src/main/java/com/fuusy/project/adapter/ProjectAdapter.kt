package com.fuusy.project.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fuusy.common.support.Constants.Companion.KEY_PROJECT_ID
import com.fuusy.project.R
import com.fuusy.project.bean.ProjectTree
import com.fuusy.project.databinding.ItemProjectAllBinding

class ProjectAdapter : RecyclerView.Adapter<ProjectAdapter.ProjectVH>() {

    private var projectTreeList: List<ProjectTree> = ArrayList()


    fun setData(projectTreeList: List<ProjectTree>) {
        this.projectTreeList = projectTreeList
        notifyDataSetChanged()
    }

    class ProjectVH(binding: ItemProjectAllBinding) : RecyclerView.ViewHolder(binding.root) {

        private val mBinding = binding

        fun bind(projectTree: ProjectTree) {
            mBinding.run {
                tvProjectTitle.text = projectTree.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectVH {

        val projectVH = ProjectVH(
            ItemProjectAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        projectVH.itemView.setOnClickListener {
            //跳转到project分类详情
            val navController = Navigation.findNavController(it)
            val bundle = Bundle()
            bundle.putInt(KEY_PROJECT_ID, projectTreeList[projectVH.layoutPosition].id)
            navController.navigate(R.id.action_fragment_project_to_projectContentFragment, bundle)
        }
        return projectVH
    }

    override fun getItemCount(): Int = projectTreeList.size

    override fun onBindViewHolder(holder: ProjectVH, position: Int) {
        holder.bind(projectTreeList[position])
    }
}