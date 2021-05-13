package com.fuusy.personal.di

import com.fuusy.common.network.RetrofitManager
import com.fuusy.personal.repo.PersonalApi
import com.fuusy.personal.repo.PersonalRepo
import com.fuusy.personal.viewmodel.PersonalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val modulePersonal = module {
    single {
        RetrofitManager.initRetrofit().getService(PersonalApi::class.java)
    }

    single {
        PersonalRepo(get())
    }

    viewModel { PersonalViewModel(get()) }
}