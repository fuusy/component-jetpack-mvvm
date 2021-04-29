package com.fuusy.jetpackkt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.fuusy.common.base.BaseActivity
import com.fuusy.common.support.Constants
import com.fuusy.home.ui.HomeFragment
import com.fuusy.jetpackkt.databinding.ActivityMainBinding

@Route(path = Constants.PATH_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private lateinit var mFragmentAdapter: VpFragmentAdapter

    override fun initData() {
        mFragmentAdapter = VpFragmentAdapter(this)
        mBinding?.vpFragment?.adapter = mFragmentAdapter
        mBinding?.vpFragment?.isUserInputEnabled = false


    }

    class VpFragmentAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 1
        }

        override fun createFragment(position: Int): Fragment {
            return HomeFragment()

        }
    }
}