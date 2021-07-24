# 组件化+Jetpack+Kotlin+MVVM

![ivo.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3a668ed3da9b425eb430856413352304~tplv-k3u1fbpfcp-watermark.image)

## 一、项目简介

![微信截图_20210521163936.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d9dc6efa53634401b7f9be3d2dc89092~tplv-k3u1fbpfcp-watermark.image)
该项目主要以`组件化+Jetpack+MVVM`为架构，使用`Kotlin`语言，集合了最新的`Jetpack`组件，如`Navigation`、`Paging3`、`Room`等，另外还加上了依赖注入框架`Koin`和图片加载框架`Coil`。

网络请求部分使用`OkHttp`+`Retrofit`，配合Kotlin的`协程`，完成了对`Retrofit和协程的请求封装`，结合`LoadSir`进行状态切换管理，让开发者只用关注自己的业务逻辑，而不要操心界面的切换和通知。

对于具体的网络封装思路，可参考

[【Jetpack篇】协程+Retrofit网络请求状态封装实战](https://juejin.cn/post/6958821338672955423)

[【Jetpack篇】协程+Retrofit网络请求状态封装实战（2）](https://juejin.cn/post/6961055228787425288)

> 项目地址：https://github.com/fuusy/wanandroid_jetpack_kt


**如果此项目对你有帮助和价值，烦请给个star,或者有什么好的建议或意见，也可以发个issues，感谢！**

## 二、项目详情
### 2.1、组件化搭建项目时暴露出的问题

##### 2.1.1、如何独立运行一个Module？

运行总App时，子Module是属于`library`，而独立运行时，子Module是属于`application`。那么我们只需要在根目录下`gradle.properties`中添加一个标志位来区分一下子Module的状态，例如`singleModule = false` ，该标志位可以用来表示当前Module是否是独立模块，`true`表示处于独立模块，可单独运行，`false`则表示是一个library。


![image-20210425094424273.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4c15b06abea8408985e19037c5063579~tplv-k3u1fbpfcp-watermark.image)

如何使用呢？

在每个`Module`的`build.gradle`中加入`singleModule`的判断，以区分是`application`还是`library`。如下：

```js
if (!singleModule.toBoolean()) {
    apply plugin: 'com.android.library'
} else {
    apply plugin: 'com.android.application'
}

......
dependencies {
}
```

如果需要独立运行只需要修改`gradle.properties`标志位`singleModule`的值。

##### 2.1.2、编译运行后，桌面会出现多个相同图标；

当新建多个Moudle的时候，运行后你会发现桌面上会出现多个相同的图标，


![微信截图_20210522184326.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d4fd4a33dc5e4334a4e1acdf9ce98da5~tplv-k3u1fbpfcp-watermark.image)

其实每个图标都能够独立运行，但是到最后App发布的时候，肯定是只需要一个总入口就可以了。

发生这种情况的原因很简单，因为新建一个`Module`，结构相当于一个project，AndroidManifest.xml包括Activity都存在，在`AndroidManifest.xml`为Activity设置了`action`和`category`，当app运行时，也就在桌面上为webview这个模块生成了一个入口。


![image-20210425102207853.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0d66db4c60d64b299255237152d071ed~tplv-k3u1fbpfcp-watermark.image)

解决方案很简单，删除上图红色框框中的代码即可。

**`但是......`** 问题又双叒叕来了，删除了<intent-filter>中代码，确实可以解决多个图标的问题，但是当该子Moudle需要独立运行时，由于缺少`<intent-filter>`中的声明，`该Module就无法正常运行`。

以下图项目为例：

![image-20210425103221979.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/99726632bb8a46b7b4176cbce47e47e6~tplv-k3u1fbpfcp-watermark.image)
我们可以在”webview“Module中，新建一个和java同层级的包，取名：manifest，将AndroidManifest.xml复制到该包下，并且将/manifest/AndroidManifest.xml中内容进行删除修改。


![image-20210425104829329.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c93e102a499a45c7817592a0fa66c7d3~tplv-k3u1fbpfcp-watermark.image)

只留有一个空壳子，原来的`AndroidManifest.xml`则保持不变。同时在webview的`build.gradle`中利用`sourceSets`进行区分。

```js
android{
    sourceSets{
        main {
            if (!singleModule.toBoolean()) {
                //如果是library，则编译manifest下AndroidManifest.xml
                manifest.srcFile 'src/main/manifest/AndroidManifest.xml'
            } else {
                //如果是application，则编译主目录下AndroidManifest.xml
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
}
```

通过修改`SourceSets`中的属性，可以指定需要被编译的源文件，根据`singleModule.toBoolean()`来判断当前Module是属于`application`还是`library`，如果是library，则编译manifest下AndroidManifest.xml，反之则直接编译主目录下AndroidManifest.xml。

上述处理后，子Moudule当作library时不会出现多个图标的情况，同时也可以独立运行。

##### 2.1.3、组件间通信

主要借助阿里的路由框架ARouter，具体使用请参考https://github.com/alibaba/ARouter


### 2.2、Jetpack组件
##### 2.2.1、Navigation
Navigation是一个管理Fragment切换的组件，支持可视化处理。开发者也完全不用操心Fragment的切换逻辑。基本使用请参考[官方说明](https://developer.android.google.cn/guide/navigation/navigation-getting-started?hl=zh-cn)

在使用`Navigation`的过程中，会出现点击back按键，界面会重新走了`onCreate`生命周期，并且将页面重构。例如`Navigation与BottomNavigationView结合时`，点击tab，Fragment会重新创建。目前比较好的解决方法是自定义`FragmentNavigator`，将内部`replace替换为show/hide`。

另外，官方对于与`BottomNavigationView`结合时的情况也提供了一种解决方案。
官方提供了一个`BottomNavigationView`的扩展函数`NavigationExtensions`，


将之前`共用一个navigation`分为每个模块`单独一个navigation`，例如该项目分为`首页`、`项目`、`我的`三个tab，相应的新建了三个navigation：`R.navigation.navi_home`, `R.navigation.navi_project`, `R.navigation.navi_personal`，
Activity中`BottomNavigationView`与`Navigation`进行绑定时也做出了相应的改变。

```js
    /**
     * navigation绑定BottomNavigationView
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(R.navigation.navi_home, R.navigation.navi_project, R.navigation.navi_personal)

        val controller = mBinding?.navView?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        
        currentNavController = controller
    }
```
官方这么做的目的在于让每个模块单独管理自己的`Fragment栈`，在tab切换时，不会相互影响。


##### 2.2,2、Paging3
Paging是一个分页组件，主要与Recyclerview结合分页加载数据。具体使用可`参考此项目“每日一问”部分`，如下：

**`UI层：`**

```js
class DailyQuestionFragment : BaseFragment<FragmentDailyQuestionBinding>() {
...

private fun loadData() {
        lifecycleScope.launchWhenCreated {
            mViewModel.dailyQuestionPagingFlow().collectLatest {
                dailyPagingAdapter.submitData(it)
            }
        }
    }
...
}
```

**`ViewModel层：`**

```java
class ArticleViewModel(private val repo: HomeRepo) : BaseViewModel(){
    /**
     * 请求每日一问数据
     */
    fun dailyQuestionPagingFlow(): Flow<PagingData<DailyQuestionData>> =
        repo.getDailyQuestion().cachedIn(viewModelScope)

}
```
**`Repository层`**


```js
class HomeRepo(private val service: HomeService, private val db: AppDatabase) : BaseRepository(){
    /**
     * 请求每日一问
     */
    fun getDailyQuestion(): Flow<PagingData<DailyQuestionData>> {

        return Pager(config) {
            DailyQuestionPagingSource(service)
        }.flow
    }
}
```

**`PagingSource层：`**

```js
/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction： 每日一问数据源，主要配合Paging3进行数据请求与显示
 */
class DailyQuestionPagingSource(private val service: HomeService) :

    PagingSource<Int, DailyQuestionData>() {
    override fun getRefreshKey(state: PagingState<Int, DailyQuestionData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DailyQuestionData> {
        return try {
            val pageNum = params.key ?: 1
            val data = service.getDailyQuestion(pageNum)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(data.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
```
##### 2.2.3、Room
`Room`是一个管理数据库的组件，此项目主要将`Paging3与Room相结合`。2.3小节主要介绍了`Paging3`从网络上加载数据分页，而这不同的是，结合`Room`需要`RemoteMediator`的协同处理。

`RemoteMediator`的**主要作用**是：可以使用此信号从网络加载更多数据并将其存储在本地数据库中，`PagingSource `可以从本地数据库加载这些数据并将其提供给界面进行显示。 当需要更多数据时，Paging 库从 `RemoteMediator` 实现调用` load() `方法。具体使用方法可参考**此项目首页文章列表部分**。

`Room`和`Paging3`结合时，`UI层`和`ViewModel层`的操作与2.3小节一致，主要修改在于`Repository`层。

**`Repository层：`**
```js
class HomeRepo(private val service: HomeService, private val db: AppDatabase) : BaseRepository() {
   /**
     * 请求首页文章，
     * Room+network进行缓存
     */
    fun getHomeArticle(articleType: Int): Flow<PagingData<ArticleData>> {
        mArticleType = articleType
        return Pager(
            config = config,
            remoteMediator = ArticleRemoteMediator(service, db, 1),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}

```

**`DAO：`**

```js
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleDataList: List<ArticleData>)

    @Query("SELECT * FROM tab_article WHERE articleType =:articleType")
    fun queryLocalArticle(articleType: Int): PagingSource<Int, ArticleData>

    @Query("DELETE FROM tab_article WHERE articleType=:articleType")
    suspend fun clearArticleByType(articleType: Int)
    
}
```

**`RoomDatabase：`**

```js
@Database(
    entities = [ArticleData::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        private const val DB_NAME = "app.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return instance ?: Room.databaseBuilder(context, AppDatabase::class.java,
                DB_NAME
            )
                .build().also {
                    instance = it
                }
        }
    }
}
```

**`自定义RemoteMediator：`**

```js
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
```
另外新创建了`RemoteKey`和`RemoteKeyDao`来管理列表的页数，具体请参考此项目home模块。

##### 2.2.4、LiveData
关于`LiveData`的使用和原理，可参考[【Jetpack篇】LiveData取代EventBus？LiveData的通信原理和粘性事件刨析](https://juejin.cn/post/6955309901363036191)

还有很多好用的Jetpack组件，将在后续更新。


## 三、感谢
**API：**
鸿洋大大提供的 WanAndroid API

**第三方开源库：**

[Retrofit](https://github.com/square/retrofit)

[OkHttp](https://github.com/square/okhttp)

[Gson](https://github.com/google/gson)

[Coil](https://github.com/coil-kt/coil)

[Koin](https://github.com/InsertKoinIO/koin)

[Arouter](https://github.com/alibaba/ARouter)

[LoadSir](https://github.com/KingJA/LoadSir)

另外还有上面没列举的一些优秀的第三方开源库，感谢开源。

## 四、版本
**持续更新**

**2021.5.20更新**

1.Paging3和Room结合；

2.将Glide替换为Coil

**2021.5.17更新**

1.新增BasePagingAdapter，减少Paging3Adapter冗余代码；

2.删除App Module Fragment的依赖。

**2021.5.12/13更新**

1.新增启动页，icon；

2.网络请求新增局部状态管理，结合loadSir切换界面，更直观简便；

3.新增Koin

**V1.0.0**

1.提交WanAndroid第一版，包括首页、个人中心、项目模块


## 五、License

MIT License

Copyright (c) 2021 fuusy



