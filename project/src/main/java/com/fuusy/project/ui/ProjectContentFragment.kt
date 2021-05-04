package com.fuusy.project.ui


import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.fuusy.common.base.BaseFragment
import com.fuusy.common.widget.FooterAdapter
import com.fuusy.common.support.Constants
import com.fuusy.project.R
import com.fuusy.project.adapter.ProjectPagingAdapter
import com.fuusy.project.databinding.FragmentProjectContentBinding
import com.fuusy.project.viewmodel.ProjectViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectContentFragment : BaseFragment<FragmentProjectContentBinding, ProjectViewModel>() {

    private val projectAdapter = ProjectPagingAdapter()

    override fun initData() {

        initTitle()

        arguments?.apply {
            val int = this.getInt(Constants.KEY_PROJECT_ID)
            lifecycleScope.launch {
                mViewModel?.loadProjectContentById(int)?.collectLatest { data ->
                    projectAdapter.submitData(data)
                }
            }
        }


        mBinding?.run {
            rvProjectContent.adapter = projectAdapter.withLoadStateFooter(FooterAdapter {

            })
        }


    }

    private fun initTitle() {
        mBinding?.run {
            llToolbar.tvTitle.text = "项目"
            llToolbar.ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun getLayoutId(): Int =
        R.layout.fragment_project_content

    override fun getViewModel(): ProjectViewModel =
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)


}