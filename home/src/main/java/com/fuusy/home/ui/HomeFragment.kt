package com.fuusy.home.ui

import androidx.lifecycle.ViewModelProviders
import com.fuusy.common.base.BaseFragment
import com.fuusy.home.R
import com.fuusy.home.adapter.viewpager.HomePageAdapter
import com.fuusy.home.databinding.FragmentHomeBinding
import com.fuusy.home.viewmodel.ArticleViewModel
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : BaseFragment<FragmentHomeBinding, ArticleViewModel>() {
    private lateinit var homePageAdapter: HomePageAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        homePageAdapter = HomePageAdapter(this)
        mBinding?.vpHome?.adapter = homePageAdapter

        TabLayoutMediator(mBinding?.homeTabLayout!!, mBinding?.vpHome!!) { tab, position ->
            when (position) {
                0 -> tab.text = "每日一问"
                1 -> tab.text = "首页"
                2 -> tab.text = "广场"
            }

        }.attach()
    }

    override fun getViewModel(): ArticleViewModel =
        ViewModelProviders.of(this).get(ArticleViewModel::class.java)
}