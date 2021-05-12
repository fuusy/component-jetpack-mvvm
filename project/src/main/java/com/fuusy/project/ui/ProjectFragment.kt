package com.fuusy.project.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.fuusy.common.base.BaseFragment
import com.fuusy.common.network.net.IStateObserver
import com.fuusy.project.R
import com.fuusy.project.adapter.ProjectAdapter
import com.fuusy.project.bean.ProjectTree
import com.fuusy.project.databinding.FragmentProjectBinding
import com.fuusy.project.viewmodel.ProjectViewModel

private const val TAG = "ProjectFragment"

class ProjectFragment : BaseFragment<FragmentProjectBinding, ProjectViewModel>() {
    private val mAdapter by lazy { ProjectAdapter() }

    override fun initData() {
        Log.d(TAG, "initData: ")
        initView()
        mViewModel?.loadProjectTree()

        mViewModel?.mProjectTreeLiveData?.observe(this,
            object : IStateObserver<List<ProjectTree>>(mBinding?.rvProjectAll) {
                override fun onDataChange(data: List<ProjectTree>?) {
                    super.onDataChange(data)
                    Log.d(TAG, "onDataChange: ")
                    data?.let { mAdapter.setData(it) }
                }

                override fun onReload(v: View?) {
                    Log.d(TAG, "onReload: ")
                    mViewModel?.loadProjectTree()
                }

                override fun onDataEmpty() {
                    super.onDataEmpty()
                    Log.d(TAG, "onDataEmpty: ")
                }

                override fun onError(e: Throwable?) {
                    super.onError(e)
                    showToast(e?.message!!)
                    Log.d(TAG, "onError: ${e?.printStackTrace()}")
                }
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