package com.fuusy.common.loadsir;

import android.content.Context;
import android.view.View;

import com.fuusy.common.R;
import com.kingja.loadsir.callback.Callback;


public class LoadingCallback extends Callback {


    //填充布局
    @Override
    protected int onCreateView() {
        return R.layout.base_layout_loading;
    }

    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
    @Override
    public boolean getSuccessVisible() {
        return true;
    }

    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
    @Override
    public void onAttach(Context context, View view) {
        super.onAttach(context, view);
    }

    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
    @Override
    public void onDetach() {
        super.onDetach();
    }

    //当前Callback的点击事件，如果返回true则覆盖注册时的onReload()，如果返回false则两者都执行，先执行onReloadEvent()。
    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
