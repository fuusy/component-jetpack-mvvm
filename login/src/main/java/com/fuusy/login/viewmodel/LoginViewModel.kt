package com.fuusy.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.fuusy.common.base.BaseViewModel
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.login.repo.LoginRepo
import com.fuusy.service.repo.LoginResp
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {
    val loginLiveData = StateLiveData<LoginResp>()
    val registerLiveData = StateLiveData<LoginResp>()
    private val repo = LoginRepo()

    fun login(userName: String, password: String) {

        viewModelScope.launch {
            repo.login(userName, password, loginLiveData)
        }

    }

    fun register(
        userName: String,
        password: String,
        rePassword: String,
    ) {
        viewModelScope.launch {
            repo.register(userName, password, rePassword, registerLiveData)
        }

    }

}