package com.fuusy.common.loadsir

import android.content.Context
import android.view.View
import com.fuusy.common.R
import com.kingja.loadsir.callback.Callback

class LoadingCallback : Callback() {
    //填充布局
    override fun onCreateView(): Int {
        return R.layout.base_layout_loading
    }

    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
    override fun getSuccessVisible(): Boolean {
        return true
    }

    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
    override fun onAttach(
        context: Context,
        view: View
    ) {
        super.onAttach(context, view)
    }

    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
    override fun onDetach() {
        super.onDetach()
    }

    //当前Callback的点击事件，如果返回true则覆盖注册时的onReload()，如果返回false则两者都执行，先执行onReloadEvent()。
    override fun onReloadEvent(
        context: Context,
        view: View
    ): Boolean {
        return true
    }
}