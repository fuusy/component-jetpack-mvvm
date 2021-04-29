package com.fuusy.home.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuusy.common.ktx.KtxPager
import com.fuusy.common.network.BaseRepository
import com.fuusy.common.network.RetrofitManager
import com.fuusy.home.ArticleData

import com.fuusy.home.api.HomeService
import com.fuusy.home.bean.BannerData
import com.fuusy.home.bean.DailyQuestionData
import com.fuusy.home.repo.data.DailyQuestionPagingSource
import com.fuusy.home.repo.data.HomeArticlePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


private const val TAG = "HomeRepo"

class HomeRepo() :
    BaseRepository() {
    private var service: HomeService? = null

    suspend fun getBanner(): List<BannerData> {
        if (service == null) {
            service = RetrofitManager.initRetrofit().getService(HomeService::class.java)
        }

        return withContext(Dispatchers.IO) {
            service?.getBanner()?.data!!
        }
    }

    private val pageSize = 20
    //请求首页文章
    fun getHomeArticle(): Flow<PagingData<ArticleData>> {
        if (service == null) {
            service = RetrofitManager.initRetrofit().getService(HomeService::class.java)
        }
        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )
        return Pager(config) {
            HomeArticlePagingSource(service!!)
        }.flow
    }

    //请求每日问答
    fun  getDailyQuestion() : Flow<PagingData<DailyQuestionData>>{
        if (service == null) {
            service = RetrofitManager.initRetrofit().getService(HomeService::class.java)
        }
        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )

        return Pager(config){
            DailyQuestionPagingSource(service!!)
        }.flow
    }
}

