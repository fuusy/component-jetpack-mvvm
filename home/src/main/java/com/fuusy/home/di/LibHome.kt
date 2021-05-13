package com.fuusy.home.di

import com.fuusy.common.network.RetrofitManager
import com.fuusy.home.api.HomeService
import com.fuusy.home.repo.HomeRepo
import com.fuusy.home.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


val moduleHome= module{
    single {
        RetrofitManager.initRetrofit().getService(HomeService::class.java)
    }

    single {
        HomeRepo(get())
    }

    viewModel { ArticleViewModel(get()) }
}