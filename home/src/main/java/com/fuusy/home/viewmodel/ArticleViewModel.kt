package com.fuusy.home.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fuusy.common.base.BaseViewModel
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.home.ArticleData
import com.fuusy.home.bean.BannerData
import com.fuusy.home.bean.DailyQuestionData
import com.fuusy.home.bean.SquareData
import com.fuusy.home.repo.HomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val TAG = "ArticleViewModel"
class ArticleViewModel(private val repo: HomeRepo) : BaseViewModel() {
    val bannerLiveData = StateLiveData<List<BannerData>>()

    /*
    /**
     * 请求首页banner
     */
    fun getBanner() {
        launch(
            { val bannerState = repo.getBanner()
                if (bannerState is ResState.Success) {
                    Log.d(TAG, "getBanner: Success")
                    bannerLiveData.postValue(bannerState.data)
                } else if (bannerState is ResState.Error){
                    Log.d(TAG, "getBanner: Error")
                    errorLiveData.postValue(bannerState.exception)
                }
            },
            {
                errorLiveData.postValue(it)
            },
            {
                loadingLiveData.postValue(false)
            })

    }

     */

    fun loadBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBanner(bannerLiveData)
        }
    }
    fun articlePagingFlow(): Flow<PagingData<ArticleData>> =
        repo.getHomeArticle().cachedIn(viewModelScope)


    fun dailyQuestionPagingFlow(): Flow<PagingData<DailyQuestionData>> =
        repo.getDailyQuestion().cachedIn(viewModelScope)


    /**
     * 查询广场数据
     */
    fun squarePagingFlow(): Flow<PagingData<SquareData>> =
        repo.getSquareData().cachedIn(viewModelScope)


}