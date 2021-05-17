package com.fuusy.common.base.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuusy.common.network.BasePagingResp
import com.fuusy.common.network.BaseResp

class BasePagingSource<T : Any>(private val block: suspend () -> BaseResp<BasePagingResp<List<T>>>,) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val pageNum = params.key ?: 1
            val respData = block.invoke()
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(respData.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}