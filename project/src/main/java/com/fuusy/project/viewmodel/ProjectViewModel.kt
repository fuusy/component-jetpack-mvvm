package com.fuusy.project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fuusy.common.base.BaseViewModel
import com.fuusy.common.network.ResState
import com.fuusy.project.bean.ProjectContent
import com.fuusy.project.bean.ProjectTree
import com.fuusy.project.repo.ProjectRepo
import kotlinx.coroutines.flow.Flow

class ProjectViewModel : BaseViewModel() {

    private val mRepo = ProjectRepo()

    val mProjectTreeLiveData = MutableLiveData<List<ProjectTree>>()

    fun loadProjectTree() {
        launch(
            {
                val state = mRepo.loadProjectTree()
                if (state is ResState.Success) {
                    mProjectTreeLiveData.postValue(state.data)
                } else if (state is ResState.Error) {
                    errorLiveData.postValue(state.exception)
                }
            },
            {
                errorLiveData.postValue(it)
            },
            {
                loadingLiveData.postValue(false)
            }
        )
    }


    fun loadProjectContentById(id: Int) : Flow<PagingData<ProjectContent>> =
        mRepo.loadContentById(id).cachedIn(viewModelScope)



}