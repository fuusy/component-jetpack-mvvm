package com.fuusy.common.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private val mOkClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .addNetworkInterceptor(LogInterceptor {
            logLevel(LogInterceptor.LogLevel.BODY)
        }).build()

    private var mRetrofit: Retrofit? = null


    fun initRetrofit(): RetrofitManager {
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .client(mOkClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return this
    }

    fun <T> getService(serviceClass: Class<T>): T {
        if (mRetrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化")
        } else {
            return mRetrofit!!.create(serviceClass)
        }
    }
}