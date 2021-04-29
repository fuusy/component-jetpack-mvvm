package com.fuusy.common.network


/**
 * 错误方法
 */
typealias Error = suspend (e: ApiException) -> Unit

open class BaseRepository() {

}