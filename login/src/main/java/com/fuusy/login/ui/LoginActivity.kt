package com.fuusy.login.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.base.BaseVmActivity
import com.fuusy.common.network.net.IStateObserver
import com.fuusy.common.support.Constants
import com.fuusy.common.support.Constants.Companion.KEY_LIVEDATA_BUS_LOGIN
import com.fuusy.common.support.Constants.Companion.SP_KEY_USER_INFO_NAME
import com.fuusy.common.support.LiveDataBus
import com.fuusy.common.utils.SpUtils
import com.fuusy.login.R
import com.fuusy.login.databinding.ActivityLoginBinding
import com.fuusy.login.viewmodel.LoginViewModel
import com.fuusy.service.repo.LoginResp
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "LoginActivity"

@Route(path = Constants.PATH_LOGIN)
class LoginActivity : BaseVmActivity<ActivityLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModel()

    override fun initData() {
        initToolbar()
        initListener()
        registerObserve()
    }

    private fun initToolbar() {
        mBinding?.run {
            setToolbarBackIcon(llToolbarLogin.ivBack, R.drawable.ic_back_clear)
            setToolbarTitle(llToolbarLogin.tvTitle, "登录")
            llToolbarLogin.ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun registerObserve() {
        mViewModel.loginLiveData.observe(this, Observer {

        })
        mViewModel.loginLiveData.observe(this, object : IStateObserver<LoginResp>(null) {
            override fun onDataChange(data: LoginResp?) {
                showToast("登录成功")
                SpUtils.put(SP_KEY_USER_INFO_NAME, data?.nickname)
                //DbHelper.insertUserInfo(this, it)
                LiveDataBus.get().with(KEY_LIVEDATA_BUS_LOGIN)
                    .postValue(data)
                finish()
            }

            override fun onReload(v: View?) {
                TODO("Not yet implemented")
            }
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


}
