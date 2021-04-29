package com.fuusy.common.network

data class BasePagingResp<T>(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int, var size: Int, var total: Int,
    var datas: T

)