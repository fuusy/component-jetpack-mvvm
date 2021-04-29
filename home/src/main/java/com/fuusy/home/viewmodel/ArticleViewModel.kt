package com.fuusy.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fuusy.common.base.BaseViewModel
import com.fuusy.home.ArticleData

import com.fuusy.home.bean.BannerData
import com.fuusy.home.bean.DailyQuestionData
import com.fuusy.home.repo.HomeRepo
import kotlinx.coroutines.flow.Flow


class ArticleViewModel() : BaseViewModel() {
    val bannerLiveData = MutableLiveData<List<BannerData>>()

    private val repo: HomeRepo = HomeRepo()

    /**
     * 请求首页banner
     */
    fun getBanner() {
        launch({
            bannerLiveData.postValue(repo.getBanner())
        },
            {
                errorLiveData.postValue(it)
            },
            {
                loadingLiveData.postValue(false)
            })
    }

    fun articlePagingFlow(): Flow<PagingData<ArticleData>> =
        repo.getHomeArticle().cachedIn(viewModelScope)


    fun dailyQuestionPagingFlow(): Flow<PagingData<DailyQuestionData>> =
        repo.getDailyQuestion().cachedIn(viewModelScope)



}