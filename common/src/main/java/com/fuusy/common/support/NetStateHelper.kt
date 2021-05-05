package com.fuusy.common.support

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.fuusy.common.R

class NetStateHelper(context: Context?, container: ViewGroup?, successView:View, layout: FrameLayout, reloadListener: OnReloadListener) {
    //请求失败时View
    private var mErrorView: View

    //请求成功时View
    private var mSuccessView: View

    //请求成功但数据为空时View
    private var mEmptyView: View
    private var reload: TextView


    init {
        mErrorView =
            LayoutInflater.from(context).inflate(R.layout.base_layout_error, container, false)
        reload = mErrorView.findViewById(R.id.tv_reload)

        //当失败时，点击重试，将事件抛给UI
        reload.setOnClickListener {
            reloadListener.onReload()
        }
        //数据为空
        mEmptyView =
            LayoutInflater.from(context).inflate(R.layout.base_layout_empty, container, false)

        mSuccessView = successView
        layout.addView(mSuccessView)
        layout.addView(mErrorView)
        layout.addView(mEmptyView)
    }

    interface OnReloadListener {
        fun onReload()
    }

    /**
     * 根据net请求状态，数据为null
     */
    fun showEmpty() {
        mSuccessView.visibility = View.GONE
        mEmptyView.visibility = View.VISIBLE
        mErrorView.visibility = View.GONE
    }

    fun showError() {
        mSuccessView.visibility = View.GONE
        mEmptyView.visibility = View.GONE
        mErrorView.visibility = View.VISIBLE
    }

    fun showSuccess() {
        mSuccessView.visibility = View.VISIBLE
        mErrorView.visibility = View.GONE
        mEmptyView.visibility = View.GONE
    }

}