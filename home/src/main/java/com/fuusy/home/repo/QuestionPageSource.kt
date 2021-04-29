package com.fuusy.home.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuusy.home.api.HomeService
import com.fuusy.home.bean.QuestionList

class QuestionPageSource(var service:HomeService) : PagingSource<Int, QuestionList>() {




    override fun getRefreshKey(state: PagingState<Int, QuestionList>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuestionList> {
        TODO("Not yet implemented")
    }
}