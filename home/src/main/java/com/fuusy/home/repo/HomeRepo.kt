package com.fuusy.home.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuusy.common.base.BaseRepository
import com.fuusy.common.network.RetrofitManager
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.home.ArticleData

import com.fuusy.home.api.HomeService
import com.fuusy.home.bean.BannerData
import com.fuusy.home.bean.DailyQuestionData
import com.fuusy.home.bean.SquareData
import com.fuusy.home.repo.data.DailyQuestionPagingSource
import com.fuusy.home.repo.data.HomeArticlePagingSource
import com.fuusy.home.repo.data.SquarePagingDataSource
import kotlinx.coroutines.flow.Flow


private const val TAG = "HomeRepo"

class HomeRepo(private val service: HomeService) :
    BaseRepository() {

    /*
    suspend fun getBanner(): ResState<List<BannerData>> {
        return executeResp(service.getBanner())
    }

     */

    /**
     * 请求首页banner
     */
    suspend fun getBanner(bannerLiveData: StateLiveData<List<BannerData>>) {
        executeResp({ service.getBanner() }, bannerLiveData)
    }

    private val pageSize = 20
    //请求首页文章
    fun getHomeArticle(): Flow<PagingData<ArticleData>> {

        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )
        return Pager(config) {
            HomeArticlePagingSource(service)
        }.flow
    }

    //请求每日问答
    fun  getDailyQuestion() : Flow<PagingData<DailyQuestionData>>{

        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )

        return Pager(config){
            DailyQuestionPagingSource(service)
        }.flow
    }

    //请求广场数据
    fun  getSquareData() : Flow<PagingData<SquareData>>{

        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )

        return Pager(config){
            SquarePagingDataSource(service)
        }.flow
    }
}

