package com.fuusy.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.fuusy.common.base.BaseActivity
import com.fuusy.common.support.Constants
import com.fuusy.common.support.Constants.Companion.KEY_WEBVIEW_PATH
import com.fuusy.common.support.Constants.Companion.KEY_WEBVIEW_TITLE
import com.fuusy.webview.databinding.ActivityWebviewBinding

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction： 动态添加和删除WebView
 */
@Route(path = Constants.PATH_WEBVIEW)
class WebviewActivity : BaseActivity<ActivityWebviewBinding>() {

    private lateinit var mWebView: WebView

    override fun initData(savedInstanceState: Bundle?) {
        val path = intent.extras?.getString(KEY_WEBVIEW_PATH)
        val title = intent.extras?.getString(KEY_WEBVIEW_TITLE)
        initToolbar(title)
        initWebView(path)
    }

    private fun initWebView(path: String?) {
        mWebView = WebView(this)
        val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mWebView.layoutParams = layoutParams
        mBinding?.flWebviewContain?.addView(mWebView)

        val webSetting = mWebView.settings
        webSetting.domStorageEnabled = true
        webSetting.useWideViewPort = true
        webSetting.loadWithOverviewMode = true
        webSetting.javaScriptEnabled = true

        mWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                dismissLoading()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showLoading()
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                dismissLoading()
            }
        }

        mWebView.loadUrl(path ?: "")
    }

    override fun getLayoutId(): Int = R.layout.activity_webview


    override fun onDestroy() {
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        mWebView.clearHistory()
        (mWebView.parent as ViewGroup).removeView(mWebView)
        mWebView.destroy()
        super.onDestroy()
    }

    private fun initToolbar(title: String?) {
        mBinding?.run {
            setToolbarBackIcon(toolbarLayout.ivBack, R.drawable.ic_back_clear)
            title?.let { setToolbarTitle(toolbarLayout.tvTitle, it) }
            toolbarLayout.ivBack.setOnClickListener {
                finish()
            }
        }
    }


}
