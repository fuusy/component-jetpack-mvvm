package com.fuusy.project.repo

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuusy.project.bean.ProjectContent

/**
 * @date：2021/6/11
 * @author fuusy
 * @instruction：项目列表Paging数据源
 */
class ProjectDataSource(private val service: ProjectApi, id: Int) :
    PagingSource<Int, ProjectContent>() {

    companion object {
        private const val TAG = "ProjectDataSource"
    }

    private val mId: Int = id

    override fun getRefreshKey(state: PagingState<Int, ProjectContent>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectContent> {
        Log.d(TAG, "load: *******")
        return try {
            var currentPage = params.key ?: 1
            val response = service.loadContentById(currentPage, mId)
            var nextPageNumber = if (currentPage < response.data?.pageCount ?: 0) {
                currentPage + 1
            } else {
                //没有更多数据
                null
            }

            return LoadResult.Page(
                data = response.data!!.datas,
                prevKey = null, // 仅向后翻页
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}