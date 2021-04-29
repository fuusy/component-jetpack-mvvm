package com.fuusy.home.ui

import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.fuusy.common.base.BaseFragment
import com.fuusy.home.R
import com.fuusy.home.adapter.paging.DailyQuestionPagingAdapter
import com.fuusy.home.adapter.paging.FooterAdapter
import com.fuusy.home.databinding.FragmentDailyQuestionBinding
import com.fuusy.home.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 每日一问
 */
class DailyQuestionFragment : BaseFragment<FragmentDailyQuestionBinding, ArticleViewModel>() {

    private val dailyPagingAdapter = DailyQuestionPagingAdapter()
    override fun getViewModel(): ArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

    override fun initData() {
        initRecyclerview()

        lifecycleScope.launch {
            mViewModel?.dailyQuestionPagingFlow()?.collectLatest {
                dailyPagingAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerview() {
        mBinding?.rvDailyQuestion?.adapter = dailyPagingAdapter.withLoadStateFooter(FooterAdapter{

        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_daily_question
    }

}