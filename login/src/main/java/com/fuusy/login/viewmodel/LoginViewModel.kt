package com.fuusy.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fuusy.common.base.BaseViewModel
import com.fuusy.common.network.ResState
import com.fuusy.login.repo.LoginRepo
import com.fuusy.service.repo.LoginResp

class LoginViewModel : BaseViewModel() {
    val loginLiveData = MutableLiveData<LoginResp>()
    val registerLiveData = MutableLiveData<LoginResp>()
    private val repo = LoginRepo()

    fun login(userName: String, password: String) {
        launch(
            {
                val respState = repo.login(userName, password)

                if (respState is ResState.Success) {
                    loginLiveData.postValue(respState.data)
                } else if (respState is ResState.Error) {
                    errorLiveData.postValue(respState.exception)
                }
            },
            { errorLiveData.postValue(it) },
            { loadingLiveData.postValue(false) }
        )
    }

    fun register(userName: String, password: String, rePassword: String) {
        launch(
            {
                val respState = repo.register(userName, password, rePassword)

                if (respState is ResState.Success) {
                    registerLiveData.postValue(respState.data)
                } else if (respState is ResState.Error) {
                    errorLiveData.postValue(respState.exception)
                }
            },{ errorLiveData.postValue(it) },
            { loadingLiveData.postValue(false) }
        )

    }

}