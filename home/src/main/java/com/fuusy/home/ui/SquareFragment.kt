package com.fuusy.home.ui

import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.fuusy.common.base.BaseFragment
import com.fuusy.home.R
import com.fuusy.home.adapter.paging.SquarePagingAdapter
import com.fuusy.home.databinding.FragmentSquareBinding
import com.fuusy.home.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 广场
 */
class SquareFragment : BaseFragment<FragmentSquareBinding>() {

    private val mViewModel: ArticleViewModel by viewModel()

    override fun initData() {
        val pagingAdapter = SquarePagingAdapter()

        mBinding?.rvSquare?.adapter = pagingAdapter


        lifecycleScope.launch {
            mViewModel.squarePagingFlow().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }


}