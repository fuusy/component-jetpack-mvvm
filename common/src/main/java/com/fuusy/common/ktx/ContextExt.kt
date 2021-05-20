package com.fuusy.common.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * @date：2021/5/19
 * @author fuusy
 * @instruction：扩展函数，判断network是否连接
 */
@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
fun Context.isConnectedNetwork(): Boolean = run {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    activeNetwork?.isConnectedOrConnecting == true
}

inline fun <reified T> String.getEmptyOrDefault(default: () -> T): T {
    return if (isNullOrEmpty() || this == "null") {
        default()
    } else {
        this as T
    }
}