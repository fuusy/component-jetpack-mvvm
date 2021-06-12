package com.fuusy.project.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuusy.common.base.BaseRepository
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.project.bean.ProjectContent
import com.fuusy.project.bean.ProjectTree
import kotlinx.coroutines.flow.Flow

/**
 * @date：2021/6/11
 * @author fuusy
 * @instruction：“项目” Repository层
 */
class ProjectRepo(private val service: ProjectApi) : BaseRepository() {
    private val pageSize = 20

    /**
     * 请求项目分类。
     * @param stateLiveData 带有请求状态的LiveData
     */
    suspend fun loadProjectTree(stateLiveData: StateLiveData<List<ProjectTree>>) {
        executeResp(
            { service.loadProjectTree() }
            , stateLiveData)
    }

    /**
     * 通过项目分类的ID，利用Paging3+Flow请求项目详细列表。
     *
     */
    fun loadContentById(id: Int): Flow<PagingData<ProjectContent>> {
        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )
        return Pager(config) {
            ProjectDataSource(service, id)
        }.flow
    }

}