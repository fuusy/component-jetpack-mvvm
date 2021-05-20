package com.fuusy.common.base

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.fuusy.common.support.StatusBar
import com.fuusy.common.widget.LoadingDialog

private const val TAG = "BaseVmActivity"

abstract class BaseVmActivity<T : ViewDataBinding> : AppCompatActivity {

    constructor() : super()

    private lateinit var mLoadingDialog: LoadingDialog

    var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        StatusBar().fitSystemBar(this)
        mLoadingDialog = LoadingDialog(this, false)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())

        initData()

    }


    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

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