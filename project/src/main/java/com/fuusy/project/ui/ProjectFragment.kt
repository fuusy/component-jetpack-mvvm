package com.fuusy.project.ui

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fuusy.common.base.BaseFragment
import com.fuusy.project.R
import com.fuusy.project.adapter.ProjectAdapter
import com.fuusy.project.databinding.FragmentProjectBinding
import com.fuusy.project.viewmodel.ProjectViewModel

private const val TAG = "ProjectFragment"
class ProjectFragment : BaseFragment<FragmentProjectBinding, ProjectViewModel>() {


    private val mAdapter by lazy { ProjectAdapter() }

    override fun initData() {
        Log.d(TAG, "initData: ")
        initView()
        mViewModel?.loadProjectTree()
        mViewModel?.mProjectTreeLiveData?.observe(this, Observer {
            mAdapter.setData(it)
        })
    }

    private fun initView() {
        mBinding?.run {
            rvProjectAll.adapter = mAdapter
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun getViewModel(): ProjectViewModel =
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)
}