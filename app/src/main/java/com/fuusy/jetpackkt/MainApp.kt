package com.fuusy.jetpackkt

import android.app.Application
import android.content.Context
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import androidx.paging.ExperimentalPagingApi
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.loadsir.EmptyCallback
import com.fuusy.common.loadsir.ErrorCallback
import com.fuusy.common.loadsir.LoadingCallback
import com.fuusy.common.utils.AppHelper
import com.fuusy.home.di.moduleHome
import com.fuusy.login.di.moduleLogin
import com.fuusy.personal.di.modulePersonal
import com.fuusy.project.di.moduleProject
import com.kingja.loadsir.core.LoadSir
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


@ExperimentalPagingApi
class MainApp : Application() {

    private val modules = arrayListOf(
        moduleHome, moduleLogin, modulePersonal, moduleProject
    )

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initLoadSir()
        initKoin()
        AppHelper.init(this.applicationContext)

    }

    //koin
    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(modules)
        }
    }


    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    private fun initARouter() {
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}