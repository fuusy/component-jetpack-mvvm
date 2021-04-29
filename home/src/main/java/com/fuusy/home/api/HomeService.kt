package com.fuusy.home.api

import com.fuusy.common.network.BasePagingResp
import com.fuusy.common.network.BaseResp
import com.fuusy.home.ArticleData
import com.fuusy.home.bean.BannerData
import com.fuusy.home.bean.DailyQuestionData
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {


    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): BaseResp<BasePagingResp<List<ArticleData>>>


    @GET("banner/json")
    suspend fun getBanner(): BaseResp<List<BannerData>>

    @GET("wenda/list/{page}/json")
    suspend fun getDailyQuestion(@Path("page") page: Int): BaseResp<BasePagingResp<List<DailyQuestionData>>>

}