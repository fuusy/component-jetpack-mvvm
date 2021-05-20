package com.fuusy.home.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuusy.home.api.HomeService
import com.fuusy.home.bean.SquareData

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：首页广场模块的数据源，主要配合Paging3进行数据请求与显示
 */
class SquarePagingDataSource(private val service: HomeService) :
    PagingSource<Int, SquareData>() {
    override fun getRefreshKey(state: PagingState<Int, SquareData>): Int? = null


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SquareData> {
        return try {
            val pageNum = params.key ?: 1
            val data = service.getSquareData(pageNum)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(data.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}