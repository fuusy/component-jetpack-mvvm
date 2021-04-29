package com.fuusy.common.base


interface IBaseResponse<T> {
    fun code(): Int
    fun msg(): String
    fun data(): T
    fun isSuccess(): Boolean
}