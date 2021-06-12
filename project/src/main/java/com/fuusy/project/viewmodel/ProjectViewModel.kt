package com.fuusy.project.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fuusy.common.base.BaseViewModel
import com.fuusy.common.network.net.StateLiveData
import com.fuusy.project.bean.ProjectContent
import com.fuusy.project.bean.ProjectTree
import com.fuusy.project.repo.ProjectRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val TAG = "ProjectViewModel"

class ProjectViewModel(private val repo: ProjectRepo) : BaseViewModel() {
    val mProjectTreeLiveData = StateLiveData<List<ProjectTree>>()


    /*
    fun loadProjectTree() {
        launch(
            {
                val state = mRepo.loadProjectTree()
                if (state is ResState.Success) {
                    mProjectTreeLiveData.postValue(state.data)
                } else if (state is ResState.Error) {
                    Log.d(TAG, "loadProjectTree: ResState.Error")
                    errorLiveData.postValue(state.exception)
                }
            },
            {
                Log.d(TAG, "loadProjectTree: postValue")
                errorLiveData.postValue(it)
            },
            {
                loadingLiveData.postValue(false)
            }
        )
    }

     */

    /**
     * 向Repository层请求项目分类。
     */
    fun loadProjectTree() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.loadProjectTree(mProjectTreeLiveData)
        }
    }

    /**
     * 根据项目ID请求项目列表详情。Paging3+Flow
     *
     * @param id 项目分类ID
     */
    fun loadProjectContentById(id: Int): Flow<PagingData<ProjectContent>> =
        repo.loadContentById(id).cachedIn(viewModelScope)


}