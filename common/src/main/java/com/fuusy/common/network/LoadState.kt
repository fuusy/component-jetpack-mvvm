package com.fuusy.common.network

sealed class LoadState(val msg: String) {
    class Loading(msg: String) : LoadState(msg)
    class Success(msg: String) : LoadState(msg)

    class Fail(msg: String) : LoadState(msg)
}