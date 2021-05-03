package com.fuusy.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.fuusy.common.support.StatusBar
import com.fuusy.common.view.LoadingDialog

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity {

    constructor() : super()
    private lateinit var mLoadingDialog: LoadingDialog

    var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        StatusBar().fitSystemBar(this)
        mLoadingDialog = LoadingDialog(this, false)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initData(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int


    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(this, false)
    }

    /**
     * dismiss loading dialog
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }
}