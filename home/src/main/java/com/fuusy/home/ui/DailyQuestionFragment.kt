package com.fuusy.home.ui

import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.fuusy.common.base.BaseFragment
import com.fuusy.common.widget.FooterAdapter
import com.fuusy.home.R
import com.fuusy.home.adapter.paging.DailyQuestionPagingAdapter
import com.fuusy.home.databinding.FragmentDailyQuestionBinding
import com.fuusy.home.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @date：2021/5/13
 * @author fuusy
 * @instruction：首页每日一问Fragment
 */
@ExperimentalPagingApi
class DailyQuestionFragment : BaseFragment<FragmentDailyQuestionBinding>() {
    private val mViewModel: ArticleViewModel by viewModel()

    private val dailyPagingAdapter = DailyQuestionPagingAdapter()

    override fun initData() {
        initRecyclerview()

        lifecycleScope.launch {
            mViewModel.dailyQuestionPagingFlow().collectLatest {
                dailyPagingAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerview() {
        mBinding?.rvDailyQuestion?.adapter = dailyPagingAdapter.withLoadStateFooter(
            FooterAdapter {
                dailyPagingAdapter.retry()
            })

        dailyPagingAdapter.addLoadStateListener {

            when (it.refresh) {

            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_daily_question
    }

}