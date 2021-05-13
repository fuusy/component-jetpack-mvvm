package com.fuusy.login.di

import com.fuusy.common.network.RetrofitManager
import com.fuusy.login.repo.LoginApi
import com.fuusy.login.repo.LoginRepo
import com.fuusy.login.viewmodel.LoginViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


val moduleLogin= module{
    single {
        RetrofitManager.initRetrofit().getService(LoginApi::class.java)
    }

    single {
        LoginRepo(get())
    }

    viewModel { LoginViewModel(get()) }
}