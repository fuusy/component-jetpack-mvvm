package com.fuusy.login.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.fuusy.common.base.BaseVmActivity
import com.fuusy.common.support.Constants
import com.fuusy.login.R
import com.fuusy.login.databinding.ActivityRegisterBinding
import com.fuusy.login.viewmodel.LoginViewModel

@Route(path = Constants.PATH_REGISTER)
class RegisterActivity : BaseVmActivity<ActivityRegisterBinding,LoginViewModel>() {

    override fun initData() {
        initToolbar()
        mBinding?.run {
            btRegister.setOnClickListener {
                mViewModel.register(etUserName.text.toString(),etPassword.text.toString(),etIvPasswordSure.text.toString())
            }
        }

        mViewModel.registerLiveData.observe(this, Observer {
            showToast("注册成功")
            finish()
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun getViewModel(): LoginViewModel =
        ViewModelProviders.of(this).get(LoginViewModel::class.java)


    private fun initToolbar() {
        mBinding?.run {
            setToolbarBackIcon(llToolbarLogin.ivBack,R.drawable.ic_back_clear)
            setToolbarTitle(llToolbarLogin.tvTitle,"注册")
            llToolbarLogin.ivBack.setOnClickListener {
                finish()
            }
        }
    }
}