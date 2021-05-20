package com.fuusy.home.ui

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import com.fuusy.common.base.BaseFragment
import com.fuusy.home.R
import com.fuusy.home.adapter.viewpager.HomePageAdapter
import com.fuusy.home.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val TAG = "HomeFragment"

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：整个首页容器，包含‘每日一问’、‘首页’、‘广场’三个fragment
 */
@ExperimentalPagingApi
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var homePageAdapter: HomePageAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        Log.d(TAG, "initData: ")
        homePageAdapter = HomePageAdapter(this)
        mBinding?.vpHome?.adapter = homePageAdapter

        mBinding?.run {
            TabLayoutMediator(homeTabLayout, vpHome) { tab, position ->
                when (position) {
                    0 -> tab.text = "每日一问"
                    1 -> tab.text = "首页"
                    2 -> tab.text = "广场"
                }
            }.attach()
            //vp_home.currentItem = 1
        }
    }

}