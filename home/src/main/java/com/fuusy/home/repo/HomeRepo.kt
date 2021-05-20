package com.fuusy.home.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuusy.common.base.BaseRepository
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.home.bean.ArticleData
import com.fuusy.home.api.HomeService
import com.fuusy.home.bean.BannerData
import com.fuusy.home.bean.DailyQuestionData
import com.fuusy.home.bean.SquareData
import com.fuusy.home.dao.db.AppDatabase
import com.fuusy.home.repo.data.DailyQuestionPagingSource
import com.fuusy.home.repo.data.SquarePagingDataSource
import com.fuusy.home.repo.inDb.ArticleRemoteMediator
import kotlinx.coroutines.flow.Flow


private const val TAG = "HomeRepo"

/**
 *
 * @date：2021/5/20
 * @author fuusy
 * @instruction： 首页Repository层，具体请求方法已封装至BaseRepository，
 *                子类请求时，调用executeResp方法即可,具体使用方法请参照com.fuusy.home.repo.HomeRepo.getBanner；
 *                关于paging3,以下getHomeArticle使用了Room+NetWork进行数据库缓存，getDailyQuestion直接使用网络请求。
 *
 *
 */
@ExperimentalPagingApi
class HomeRepo(private val service: HomeService, private val db: AppDatabase) : BaseRepository() {
    private var mArticleType: Int = 0
    companion object {
        private const val PAGE_SIZE = 10
        val config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 5,
            initialLoadSize = 10,
            enablePlaceholders = false,
            maxSize = PAGE_SIZE * 3
        )
    }

    /**
     * 请求首页banner
     */
    suspend fun getBanner(bannerLiveData: StateLiveData<List<BannerData>>) {
        executeResp({ service.getBanner() }, bannerLiveData)
    }


    private val pagingSourceFactory = { db.articleDao().queryLocalArticle(mArticleType) }

    /**
     * 请求首页文章，
     * Room+network进行缓存
     */
    fun getHomeArticle(articleType: Int): Flow<PagingData<ArticleData>> {
        mArticleType = articleType
        return Pager(
            config = config,
            remoteMediator = ArticleRemoteMediator(service, db, 1),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    /**
     * 请求每日一问
     */
    fun getDailyQuestion(): Flow<PagingData<DailyQuestionData>> {

        return Pager(config) {
            DailyQuestionPagingSource(service)
        }.flow
    }

    /**
     * 请求广场数据
     */
    fun getSquareData(): Flow<PagingData<SquareData>> {
        return Pager(config) {
            SquarePagingDataSource(service)
        }.flow
    }
}

