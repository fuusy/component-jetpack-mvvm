package com.fuusy.home.ui

import androidx.lifecycle.ViewModelProviders
import com.fuusy.common.base.BaseFragment
import com.fuusy.home.R
import com.fuusy.home.databinding.FragmentSquareBinding
import com.fuusy.home.viewmodel.ArticleViewModel

/**
 * 广场
 */
class SquareFragment : BaseFragment<FragmentSquareBinding, ArticleViewModel>() {

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }

    override fun getViewModel(): ArticleViewModel =
        ViewModelProviders.of(this).get(ArticleViewModel::class.java)


}