package com.fuusy.login.repo

import com.fuusy.common.base.BaseRepository
import com.fuusy.common.network.ResState
import com.fuusy.common.network.RetrofitManager
import com.fuusy.service.repo.LoginResp

class LoginRepo : BaseRepository() {

    private val service = RetrofitManager.initRetrofit().getService(LoginApi::class.java)

    suspend fun login(userName: String, password: String): ResState<LoginResp> {
        return executeResp(service.login(userName, password))
    }

    suspend fun register(
        userName: String,
        password: String,
        rePassword: String
    ): ResState<LoginResp> {
        return executeResp(service.register(userName, password, rePassword))
    }

}