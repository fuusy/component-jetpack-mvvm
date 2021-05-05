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
import com.fuusy.common.R
import com.fuusy.common.databinding.BaseFragmentLayoutBinding
import com.fuusy.common.loadsir.EmptyCallback
import com.fuusy.common.loadsir.ErrorCallback
import com.fuusy.common.network.IStateView
import com.fuusy.common.support.Constants
import com.fuusy.common.support.NetStateHelper
import com.fuusy.common.utils.SpUtils
import com.fuusy.common.view.LoadingDialog
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import retrofit2.HttpException
import java.net.SocketTimeoutException

private const val TAG = "BaseFragment"

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    NetStateHelper.OnReloadListener {

    var mBinding: T? = null
    var mViewModel: VM? = null
    private lateinit var mContext: Context
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var loadService: LoadService<Any>
    private lateinit var mBaseContainBinding: BaseFragmentLayoutBinding

    //请求失败时View
    private lateinit var errorView: View

    //请求成功时View
    private lateinit var successView: View

    //请求成功但数据为空时View
    private lateinit var emptyView: View
    private lateinit var netStateHelper: NetStateHelper
    


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBaseContainBinding =
            DataBindingUtil.inflate(inflater, R.layout.base_fragment_layout, container, false)
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        netStateHelper = NetStateHelper(context,container,mBinding?.root!!,mBaseContainBinding.baseContainer,this)

        showSuccess()
        return mBaseContainBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = LoadingDialog(view.context, false)
        mViewModel = getViewModel()

        mViewModel?.loadingLiveData?.observe(viewLifecycleOwner, Observer {
            if (it) {
                //show loading
                showLoading()
            } else {
                Log.d(TAG, "onViewCreated: not show loading")
                dismissLoading()
            }
        })
        mViewModel?.errorLiveData?.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: error ")
            showError()
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
                } else if (e.code() == 404) {
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


    /**
     * 根据net请求状态，数据为null
     */
    protected fun showEmpty() {
        netStateHelper.showEmpty()
    }

    protected fun showError() {
        netStateHelper.showError()
    }

    protected fun showSuccess() {
        netStateHelper.showSuccess()
    }

    override fun onReload() {
        onRetry()
    }

    protected fun onRetry() {
        Log.d(TAG, "onRetry: ")
    }
}