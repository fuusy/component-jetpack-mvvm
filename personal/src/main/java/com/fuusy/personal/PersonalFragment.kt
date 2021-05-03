package com.fuusy.personal

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.BaseFragment
import com.fuusy.common.support.Constants
import com.fuusy.common.support.Constants.Companion.KEY_LIVEDATA_BUS_LOGIN
import com.fuusy.common.support.LiveDataBus
import com.fuusy.personal.databinding.FragmentPersonalBinding
import com.fuusy.personal.viewmodel.PersonalViewModel

class PersonalFragment : BaseFragment<FragmentPersonalBinding, PersonalViewModel>() {
    override fun initData() {

        mBinding?.run {
            ivUserIcon.setOnClickListener {
                ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()
            }
        }

        LiveDataBus.get().with(KEY_LIVEDATA_BUS_LOGIN).observe(this, Observer {

        })
    }

    override fun getLayoutId(): Int = R.layout.fragment_personal

    override fun getViewModel(): PersonalViewModel =
        ViewModelProviders.of(this).get(PersonalViewModel::class.java)


}