package com.fuusy.home.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fuusy.common.base.BaseViewModel
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.home.bean.ArticleData
import com.fuusy.home.bean.BannerData
import com.fuusy.home.bean.DailyQuestionData
import com.fuusy.home.bean.SquareData
import com.fuusy.home.repo.HomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val TAG = "ArticleViewModel"

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：
 */
@ExperimentalPagingApi
class ArticleViewModel(private val repo: HomeRepo) : BaseViewModel() {
    val bannerLiveData = StateLiveData<List<BannerData>>()

    fun loadBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBanner(bannerLiveData)
        }
    }

    /**
     * 请求首页文章数据
     */
    fun articlePagingFlow(): Flow<PagingData<ArticleData>> =
        repo.getHomeArticle(1).cachedIn(viewModelScope)


    /**
     * 请求每日一问数据
     */
    fun dailyQuestionPagingFlow(): Flow<PagingData<DailyQuestionData>> =
        repo.getDailyQuestion().cachedIn(viewModelScope)


    /**
     * 查询广场数据
     */
    fun squarePagingFlow(): Flow<PagingData<SquareData>> =
        repo.getSquareData().cachedIn(viewModelScope)


}