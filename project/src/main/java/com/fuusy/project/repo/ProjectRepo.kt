package com.fuusy.project.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuusy.common.base.BaseRepository
import com.fuusy.common.network.ResState
import com.fuusy.common.network.RetrofitManager
import com.fuusy.project.bean.ProjectContent
import com.fuusy.project.bean.ProjectTree
import kotlinx.coroutines.flow.Flow

class ProjectRepo : BaseRepository() {

    private lateinit var mService: ProjectApi

    init {
        mService = RetrofitManager.initRetrofit().getService(ProjectApi::class.java)
    }

    suspend fun loadProjectTree(): ResState<List<ProjectTree>> {
        return executeResp(mService.loadProjectTree())
    }

    private val pageSize = 20
    //请求首页文章
    fun loadContentById(id: Int): Flow<PagingData<ProjectContent>> {
        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )
        return Pager(config) {
            ProjectDataSource(mService, id)
        }.flow
    }

}