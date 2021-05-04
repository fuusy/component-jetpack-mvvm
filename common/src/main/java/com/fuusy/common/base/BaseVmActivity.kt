package com.fuusy.common.base

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.fuusy.common.support.StatusBar
import com.fuusy.common.view.LoadingDialog
import retrofit2.HttpException
import java.net.SocketTimeoutException

private const val TAG = "BaseVmActivity"

abstract class BaseVmActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity {

    constructor() : super()

    private lateinit var mLoadingDialog: LoadingDialog

    var mBinding: T? = null
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        StatusBar().fitSystemBar(this)
        mLoadingDialog = LoadingDialog(this, false)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = getViewModel()

        mViewModel.loadingLiveData.observe(this, Observer {
            if (it) {
                //show loading
                showLoading()
            } else {
                dismissLoading()
            }
        })

        mViewModel.errorLiveData.observe(this, Observer {
            Log.d(TAG, "onViewCreated: error ${it.message}")
            throwableHandler(it)
        })
        initData()

    }

    /**
     * 请求时网络异常的处理
     */
    private fun throwableHandler(e: Throwable) {
        when (e) {
            is SocketTimeoutException -> showToast("连接超时")
            is HttpException -> {
                if (e.code() == 504) {
                    showToast("网络异常，请检查您的网络状态")
                } else if (e.code() == 404) {
                    showToast("请求地址不存在")
                }
            }
            else -> e.message?.let { showToast(it) }
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    abstract fun getViewModel(): VM

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    abstract fun initData()

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

    /**
     * 设置toolbar名称
     */
    protected fun setToolbarTitle(view: TextView, title: String) {
        view.text = title
    }

    /**
     * 设置toolbar返回按键图片
     */
    protected fun setToolbarBackIcon(view: ImageView, id: Int) {
        view.setBackgroundResource(id)
    }

}