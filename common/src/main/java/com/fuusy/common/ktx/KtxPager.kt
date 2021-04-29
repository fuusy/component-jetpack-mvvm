package com.fuusy.common.ktx

import androidx.paging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class KtxPager<K : Any, V : Any>(
    private val pageSize: Int,
    private val prefetchDistance: Int,
    private val initialLoadSize: Int,
    private val maxSize: Int,
    private val enablePlaceholders: Boolean,
    private val scope: CoroutineScope,
    private val pagingSource: () -> PagingSource<K, V>? = { null },
    private val loadData:
    suspend (PagingSource.LoadParams<K>) -> PagingSource.LoadResult<K, V>? = { null }
) {

    fun loadData(): Flow<PagingData<V>> {
        return Pager(
            PagingConfig(pageSize, prefetchDistance, enablePlaceholders, initialLoadSize, maxSize)
        ) {
            pagingSource() ?: object : PagingSource<K, V>() {
                override fun getRefreshKey(state: PagingState<K, V>): K? = null

                override suspend fun load(params: LoadParams<K>): LoadResult<K, V> {
                    return loadData(params) ?: throw IllegalArgumentException(
                        "one of pagingSource or loadData must not null"
                    )
                }

            }
        }.flow.cachedIn(scope)


    }

}