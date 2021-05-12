package com.fuusy.project.repo

import com.fuusy.common.network.BasePagingResp
import com.fuusy.common.network.BaseResp
import com.fuusy.project.bean.ProjectContent
import com.fuusy.project.bean.ProjectTree
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {

    @GET("project/tree/json")
    suspend fun loadProjectTree(): BaseResp<List<ProjectTree>>


    @GET("project/list/{path}/json")
    suspend fun loadContentById(
        @Path("path") path: Int,
        @Query("cid") cid: Int
    ): BaseResp<BasePagingResp<List<ProjectContent>>>


}