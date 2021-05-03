package com.fuusy.common.network

import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * 用来封装业务错误信息
 *
 */
class ApiException(val throwable: Throwable) {

}