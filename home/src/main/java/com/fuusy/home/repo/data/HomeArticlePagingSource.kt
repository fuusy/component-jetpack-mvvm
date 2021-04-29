package com.fuusy.home.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuusy.home.ArticleData
import com.fuusy.home.api.HomeService

private const val TAG = "HomeArticlePagingSource"

class HomeArticlePagingSource(private var service: HomeService) : PagingSource<Int, ArticleData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val pageNum = params.key ?: 1
            val homeData = service.getHomeArticle(pageNum)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(homeData.data.datas, prevKey = preKey, nextKey = pageNum + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? = null

}