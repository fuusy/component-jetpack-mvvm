package com.fuusy.common.network

import android.net.ParseException
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.fuusy.common.loadsir.EmptyCallback
import com.fuusy.common.loadsir.ErrorCallback
import com.fuusy.common.loadsir.LoadingCallback
import com.google.gson.JsonParseException
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.Callback.OnReloadListener
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

abstract class IObserve<T>(targetView: View) : Observer<BaseResp<T>>, OnReloadListener {

    private var loadService: LoadService<Any>
    override fun onChanged(tBaseStateResp: BaseResp<T>) {
        Log.d(TAG, tBaseStateResp.toString())
        if (tBaseStateResp.isSuccess()) {
            onDataChanged(tBaseStateResp.data)
        }

        loadService.showWithConvertor(tBaseStateResp)
    }

    fun onDataNull() {}

    abstract fun onDataChanged(t: T)

    fun onError(throwable: Throwable?) {
        try {
            if (throwable is SocketTimeoutException) {
                showToast("请求超时，请稍后再试")
            } else if (throwable is ConnectException) {
                showToast("网络连接超时，请检查网络状态")
            } else if (throwable is SSLHandshakeException) {
                showToast("安全证书异常")
            } else if (throwable is HttpException) {
                val code = throwable.code()
                if (code == 504) {
                    showToast("网络异常，请检查您的网络状态")
                } else if (code == 404) {
                    showToast("请求地址不存在")
                } else {
                }
            } else if (throwable is UnknownHostException) {
                showToast("网络异常，请检查您的网络状态")
            } else {
                //showToast(throwable.getMessage());
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    fun onFail(reason: String) {
        showToast(reason)
    }

    private fun showToast(msg: String) {

    }

    companion object {
        private const val TAG = "IStateObserver"
    }

    init {

        loadService = LoadSir.getDefault()
            .register(targetView, this, Convertor<BaseResp<T>> { tBaseStateResp ->
                    var resultCode: Class<out Callback?> =
                        SuccessCallback::class.java
                    when (tBaseStateResp.dataState) {
                        DataState.STATE_LOADING -> resultCode =
                            LoadingCallback::class.java
                        DataState.STATE_SUCCESS -> resultCode = SuccessCallback::class.java
                        DataState.STATE_EMPTY, DataState.STATE_FAILED -> resultCode = EmptyCallback::class.java
                        DataState.STATE_ERROR -> {
                            val error: Throwable = tBaseStateResp.error!!
                            onError(error)
                            if (error is HttpException) {
                                //网络错误
                            } else if (error is ConnectException) {
                                //无网络连接
                            } else if (error is InterruptedIOException) {
                                //连接超时
                            } else if (error is JsonParseException
                                || error is JSONException
                                || error is ParseException
                            ) {
                                //解析错误
                            } else {
                                //未知错误
                            }
                            resultCode = ErrorCallback::class.java
                        }

                    }
                    resultCode
                })
    }

}


