package com.fuusy.home.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuusy.home.api.HomeService
import com.fuusy.home.bean.ArticleData

private const val TAG = "HomeArticlePagingSource"

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：首页文章的pagingSource，主要配合Paging3进行数据请求与显示
 */
class HomeArticlePagingSource(private var service: HomeService) : PagingSource<Int, ArticleData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val pageNum = params.key ?: 1
            val homeData = service.getHomeArticle(pageNum)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(homeData.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? = null

}