package com.fuusy.webview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import androidx.core.view.isVisible
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
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == null) {
                    return false
                }
                try {
                    if (url.startsWith("weixin://") || url.startsWith("jianshu://")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                } catch (e: Exception) {
                    return true
                }
                view?.loadUrl(url)
                return true
            }
        }

        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                mBinding?.progressBar?.isVisible = newProgress != 100
                mBinding?.progressBar?.progress = newProgress
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
