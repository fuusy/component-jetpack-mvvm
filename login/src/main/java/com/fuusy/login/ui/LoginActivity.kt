package com.fuusy.login.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.BaseVmActivity
import com.fuusy.common.support.Constants
import com.fuusy.common.support.Constants.Companion.KEY_LIVEDATA_BUS_LOGIN
import com.fuusy.common.support.LiveDataBus
import com.fuusy.login.R
import com.fuusy.login.databinding.ActivityLoginBinding
import com.fuusy.login.viewmodel.LoginViewModel

private const val TAG = "LoginActivity"

@Route(path = Constants.PATH_LOGIN)
class LoginActivity : BaseVmActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun initData() {
        initListener()
        registerObserve()
    }

    private fun registerObserve() {
        mViewModel.loginLiveData.observe(this, Observer {
            showToast("登录成功")
            LiveDataBus.get().with(KEY_LIVEDATA_BUS_LOGIN)
                .postValue(it)
            finish()
        })
    }

    private fun initListener() {
        mBinding?.run {
            tvToRegister.setOnClickListener {
                ARouter.getInstance().build(Constants.PATH_REGISTER)
                    .navigation()
            }

            btLogin.setOnClickListener {
                mViewModel.login(etUserName.text.toString(), etPassword.text.toString())
            }
        }


    }

    override fun getLayoutId(): Int = R.layout.activity_login
    override fun getViewModel(): LoginViewModel =
        ViewModelProviders.of(this).get(LoginViewModel::class.java)


}
