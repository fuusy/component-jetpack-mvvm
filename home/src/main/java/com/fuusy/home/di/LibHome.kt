package com.fuusy.home.di

import androidx.paging.ExperimentalPagingApi
import com.fuusy.common.network.RetrofitManager
import com.fuusy.home.api.HomeService
import com.fuusy.home.dao.db.AppDatabase
import com.fuusy.home.repo.HomeRepo
import com.fuusy.home.viewmodel.ArticleViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalPagingApi
val moduleHome = module {
    single {
        RetrofitManager.initRetrofit().getService(HomeService::class.java)
    }

    single {
        AppDatabase.get(androidApplication())
    }

    single {
        HomeRepo(get(),get())
    }

    viewModel { ArticleViewModel(get()) }
}