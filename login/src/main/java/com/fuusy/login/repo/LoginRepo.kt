package com.fuusy.login.repo

import com.fuusy.common.base.BaseRepository
import com.fuusy.common.network.RetrofitManager
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.service.repo.LoginResp

class LoginRepo( private val service:LoginApi) : BaseRepository() {

    suspend fun login(userName: String, password: String, stateLiveData: StateLiveData<LoginResp>) {
        executeResp({ service.login(userName, password) }, stateLiveData)
    }

    suspend fun register(
        userName: String,
        password: String,
        rePassword: String,
        stateLiveData: StateLiveData<LoginResp>
    ) {
        executeResp({ service.register(userName, password, rePassword) }, stateLiveData)
    }

}