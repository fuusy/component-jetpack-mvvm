package com.fuusy.jetpackkt

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.fuusy.common.loadsir.EmptyCallback
import com.fuusy.common.loadsir.ErrorCallback
import com.fuusy.common.loadsir.LoadingCallback
import com.kingja.loadsir.core.LoadSir

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initLoadSir()
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