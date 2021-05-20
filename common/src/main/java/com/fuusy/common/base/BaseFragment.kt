package com.fuusy.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.fuusy.common.R
import com.fuusy.common.databinding.BaseFragmentLayoutBinding
import com.fuusy.common.support.Constants
import com.fuusy.common.utils.SpUtils
import com.fuusy.common.widget.LoadingDialog
import com.kingja.loadsir.core.LoadService

private const val TAG = "BaseFragment"

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    var mBinding: T? = null
    private lateinit var mContext: Context
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var loadService: LoadService<Any>
    private lateinit var mBaseContainBinding: BaseFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBaseContainBinding =
            DataBindingUtil.inflate(inflater, R.layout.base_fragment_layout, container, false)
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        mBaseContainBinding.baseContainer.addView(mBinding?.root)
        return mBaseContainBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = LoadingDialog(view.context, false)
        initData()
    }

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun getLayoutId(): Int


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

    private var time: Long = 0
    private var oldMsg: String? = null

    /**
     * 相同msg 只显示一个。
     */
    fun showToast(msg: String) {
        if (msg != oldMsg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
            time = System.currentTimeMillis()
        } else {
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                time = System.currentTimeMillis()
            }
        }
        oldMsg = msg
    }

    protected fun isLogin(): Boolean {
        val userName = SpUtils.getString(Constants.SP_KEY_USER_INFO_NAME)
        if (userName == null || userName.isEmpty()) {
            return false
        }
        return true
    }

}