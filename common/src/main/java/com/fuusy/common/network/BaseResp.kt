package com.fuusy.common.network

/**
 * json返回的基本类型
 */
data class BaseResp<T>(
    var errorCode: Int,
    var errorMsg: String,
    var data: T
)