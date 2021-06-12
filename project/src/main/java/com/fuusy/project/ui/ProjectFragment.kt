package com.fuusy.project.ui

import android.util.Log
import android.view.View
import com.fuusy.common.base.BaseFragment
import com.fuusy.common.network.net.IStateObserver
import com.fuusy.project.R
import com.fuusy.project.adapter.ProjectAdapter
import com.fuusy.project.bean.ProjectTree
import com.fuusy.project.databinding.FragmentProjectBinding
import com.fuusy.project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @date：2021/6/11
 * @author fuusy
 * @instruction：‘项目’Fragment
 */
class ProjectFragment : BaseFragment<FragmentProjectBinding>() {

    private val mViewModel: ProjectViewModel by viewModel()
    private val mAdapter by lazy { ProjectAdapter() }

    companion object {
        private const val TAG = "ProjectFragment"
    }

    override fun initData() {
        Log.d(TAG, "initData: ")
        initView()
        mViewModel.loadProjectTree()

        mViewModel.mProjectTreeLiveData.observe(this,
            object : IStateObserver<List<ProjectTree>>(mBinding?.rvProjectAll) {
                override fun onDataChange(data: List<ProjectTree>?) {
                    super.onDataChange(data)
                    Log.d(TAG, "onDataChange: ")
                    data?.let { mAdapter.setData(it) }
                }

                override fun onReload(v: View?) {
                    Log.d(TAG, "onReload: ")
                    mViewModel.loadProjectTree()
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

}