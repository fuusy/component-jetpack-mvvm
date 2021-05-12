package com.fuusy.project.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuusy.project.bean.ProjectContent

class ProjectDataSource(private val service: ProjectApi, id: Int) :
    PagingSource<Int, ProjectContent>() {

    private val mId: Int = id

    override fun getRefreshKey(state: PagingState<Int, ProjectContent>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectContent> {
        return try {
            val pageNum = params.key ?: 1
            val data = service.loadContentById(pageNum, mId)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(data.data!!.datas, prevKey = preKey, nextKey = pageNum + 1)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}