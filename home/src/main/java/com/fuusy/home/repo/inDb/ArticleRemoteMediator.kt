package com.fuusy.home.repo.inDb

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.fuusy.home.bean.ArticleData
import com.fuusy.home.api.HomeService
import com.fuusy.home.bean.RemoteKey
import com.fuusy.home.dao.db.AppDatabase
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "ArticleRemoteMediator"

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：RemoteMediator 的主要作用是：在 Pager 耗尽数据或现有数据失效时，从网络加载更多数据。
 * 可以使用此信号从网络加载更多数据并将其存储在本地数据库中，PagingSource 可以从本地数据库加载这些数据并将其提供给界面进行显示。
 * 当需要更多数据时，Paging 库从 RemoteMediator 实现调用 load() 方法。这是一项挂起功能，因此可以放心地执行长时间运行的工作。
 * 此功能通常从网络源提取新数据并将其保存到本地存储空间。
 * 此过程会处理新数据，但长期存储在数据库中的数据需要进行失效处理（例如，当用户手动触发刷新时）。
 * 这由传递到 load() 方法的 LoadType 属性表示。LoadType 会通知 RemoteMediator 是需要刷新现有数据，还是提取需要附加或前置到现有列表的更多数据。
 */
@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val api: HomeService,
    private val db: AppDatabase,
    private val articleType: Int
) : RemoteMediator<Int, ArticleData>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleData>
    ): MediatorResult {

        /*
        1.LoadType.REFRESH：首次访问 或者调用 PagingDataAdapter.refresh() 触发
        2.LoadType.PREPEND：在当前列表头部添加数据的时候时触发，实际在项目中基本很少会用到直接返回 MediatorResult.Success(endOfPaginationReached = true) ，参数 endOfPaginationReached 表示没有数据了不在加载
        3.LoadType.APPEND：加载更多时触发，这里获取下一页的 key, 如果 key 不存在，表示已经没有更多数据，直接返回 MediatorResult.Success(endOfPaginationReached = true) 不会在进行网络和数据库的访问
         */
        try {
            Log.d(TAG, "load: $loadType")
            val pageKey: Int? = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    //使用remoteKey来获取下一个或上一个页面。
                    val remoteKey =
                        state.lastItemOrNull()?.id?.let {
                            db.remoteKeyDao().remoteKeysArticleId(it, articleType)
                        }

                    //remoteKey' null '，这意味着在初始刷新后没有加载任何项目，也没有更多的项目要加载。
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(true)
                    }
                    remoteKey.nextKey
                }
            }

            val page = pageKey ?: 0
            //从网络上请求数据
            val result = api.getHomeArticle(page).data?.datas
            result?.forEach {
                it.articleType = articleType
            }
            val endOfPaginationReached = result?.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //清空数据
                    db.remoteKeyDao().clearRemoteKeys(articleType)
                    db.articleDao().clearArticleByType(articleType)
                }
                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached!!) null else page + 1
                val keys = result.map {
                    RemoteKey(
                        articleId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        articleType = articleType
                    )
                }
                db.remoteKeyDao().insertAll(keys)
                db.articleDao().insertArticle(articleDataList = result)
            }
            return MediatorResult.Success(endOfPaginationReached!!)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }

    }
}