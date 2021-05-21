package com.fuusy.home.ui

import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fuusy.common.base.BaseFragment
import com.fuusy.home.R
import com.fuusy.home.adapter.paging.SquarePagingAdapter
import com.fuusy.home.databinding.FragmentSquareBinding
import com.fuusy.home.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：首页广场
 */
@ExperimentalPagingApi
class SquareFragment : BaseFragment<FragmentSquareBinding>() {

    private val mViewModel: ArticleViewModel by viewModel()

    override fun initData() {
        val pagingAdapter = SquarePagingAdapter()
        mBinding?.rvSquare?.adapter = pagingAdapter

        lifecycleScope.launchWhenCreated {
            mViewModel.squarePagingFlow().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
        initListener(pagingAdapter)
    }

    private fun initListener(pagingAdapter: SquarePagingAdapter) {
        //下拉刷新
        mBinding?.swipeLayout?.setOnRefreshListener { pagingAdapter.refresh() }
        lifecycleScope.launchWhenCreated {
            pagingAdapter.loadStateFlow.collectLatest {
                //根据Paging的请求状态收缩刷新view
                mBinding?.swipeLayout?.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }


}