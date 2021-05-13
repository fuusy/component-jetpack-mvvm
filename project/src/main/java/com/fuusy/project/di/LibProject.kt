package com.fuusy.project.di

import com.fuusy.common.network.RetrofitManager
import com.fuusy.project.repo.ProjectApi
import com.fuusy.project.repo.ProjectRepo
import com.fuusy.project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val moduleProject = module {
    single {
        RetrofitManager.initRetrofit().getService(ProjectApi::class.java)
    }

    single {
        ProjectRepo(get())
    }

    viewModel { ProjectViewModel(get()) }
}