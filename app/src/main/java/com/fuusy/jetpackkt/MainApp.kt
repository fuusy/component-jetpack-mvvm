package com.fuusy.jetpackkt

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initARouter()
    }

    private fun initARouter() {
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }
}