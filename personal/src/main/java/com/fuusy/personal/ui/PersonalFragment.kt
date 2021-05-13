package com.fuusy.personal.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.BaseFragment
import com.fuusy.common.support.Constants
import com.fuusy.common.support.Constants.Companion.KEY_LIVEDATA_BUS_LOGIN
import com.fuusy.common.support.LiveDataBus
import com.fuusy.common.utils.SpUtils
import com.fuusy.personal.R
import com.fuusy.personal.databinding.FragmentPersonalBinding
import com.fuusy.personal.viewmodel.PersonalViewModel
import com.fuusy.service.repo.LoginResp
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonalFragment : BaseFragment<FragmentPersonalBinding>() {

    private val mViewModel: PersonalViewModel by viewModel()

    override fun initData() {

        mBinding?.run {
            if (!isLogin()) {
                //没有登录，点击头像跳到登录界面
                ivUserIcon.setOnClickListener {
                    ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()
                }

            } else {
                tvNameUser.text = SpUtils.getString(Constants.SP_KEY_USER_INFO_NAME)
            }

        }


        //登录成功后触发的事件
        LiveDataBus.get().with(KEY_LIVEDATA_BUS_LOGIN, LoginResp::class.java)
            .observe(this, Observer {
                mBinding?.run {
                    tvNameUser.text = it.nickname
                }
            })
    }

    override fun getLayoutId(): Int =
        R.layout.fragment_personal


}