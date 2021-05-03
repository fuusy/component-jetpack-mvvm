package com.fuusy.common.network

import java.lang.Exception

sealed class ResState<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResState<T>()
    data class Error(val exception: Exception) : ResState<Nothing>()
}