package com.fuusy.common.utils

import android.content.Context

/**
 * @date：2021/5/19
 * @author fuusy
 * @instruction：
 */
object AppHelper {
    lateinit var mContext: Context

    fun init(context: Context) {
        this.mContext = context
    }
}