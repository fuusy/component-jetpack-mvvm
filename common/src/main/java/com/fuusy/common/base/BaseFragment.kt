package com.fuusy.common.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fuusy.common.support.Constants
import com.fuusy.common.utils.SpUtils
import com.fuusy.common.view.LoadingDialog
import retrofit2.HttpException
import java.net.SocketTimeoutException

private const val TAG = "BaseFragment"
abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment {


    constructor() : super()

    var mBinding: T? = null
    var mViewModel: VM? = null
    private lateinit var mContext: Context
    private lateinit var mLoadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = LoadingDialog(view.context, false)

        mViewModel = getViewModel()

        mViewModel?.loadingLiveData?.observe(this, Observer {
            if (it) {
                //show loading
                Log.d(TAG, "onViewCreated: show loading")
                showLoading()
            } else {
                Log.d(TAG, "onViewCreated: not show loading")
                dismissLoading()
            }
        })

        mViewModel?.errorLiveData?.observe(this, Observer {
            Log.d(TAG, "onViewCreated: error ${it.message}")
            throwableHandler(it)
        })
        initData()
    }

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM

    /**
     * show 加载中
     */
    private fun showLoading() {
        mLoadingDialog.showDialog(mContext, false)
    }

    /**
     * dismiss loading dialog
     */
    private fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }


    private fun throwableHandler(e: Throwable) {
        when (e) {
            is SocketTimeoutException -> showToast("连接超时")
            is HttpException -> {
                if (e.code() == 504) {
                    showToast("网络异常，请检查您的网络状态")
                }else if (e.code() == 404) {
                    showToast("请求地址不存在")
                }
            }
            else -> e.message?.let { showToast(it) }
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }


    protected fun isLogin(): Boolean {
        val userName = SpUtils.getString(Constants.SP_KEY_USER_INFO_NAME)
        if (userName == null || userName.isEmpty()) {
            return false
        }
        return true
    }
}