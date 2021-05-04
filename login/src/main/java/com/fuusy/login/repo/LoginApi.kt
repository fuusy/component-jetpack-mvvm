package com.fuusy.login.repo

import com.fuusy.common.network.BaseResp
import com.fuusy.service.repo.LoginResp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(@Field("username") username: String,@Field("password") password: String
                         ,@Field("repassword") repassword: String) : BaseResp<LoginResp>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String,@Field("password") password: String): BaseResp<LoginResp>

}