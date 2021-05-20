package com.fuusy.home.ui

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import coil.load

import com.fuusy.common.base.BaseFragment
import com.fuusy.common.network.net.IStateObserver
import com.fuusy.common.widget.FooterAdapter
import com.fuusy.home.R
import com.fuusy.home.adapter.paging.HomeArticlePagingAdapter
import com.fuusy.home.bean.BannerData
import com.fuusy.home.databinding.FragmentArticleBinding
import com.fuusy.home.viewmodel.ArticleViewModel
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "ArticleFragment"

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：首页文章列表Fragment，包含banner和热门文章列表
 */
@ExperimentalPagingApi
class ArticleFragment : BaseFragment<FragmentArticleBinding>() {

    private val mViewModel: ArticleViewModel by viewModel()

    private var mArticlePagingAdapter =
        HomeArticlePagingAdapter()

    override fun initData() {
        initListener()

        mBinding?.rvHomeArticle?.adapter = mArticlePagingAdapter.withLoadStateFooter(
            FooterAdapter {
                //重新请求
                mArticlePagingAdapter.retry()
            })

        mViewModel.loadBanner()
        mViewModel.bannerLiveData.observe(this, object : IStateObserver<List<BannerData>>(null) {
            override fun onDataChange(data: List<BannerData>?) {
                super.onDataChange(data)
                //绑定banner
                mBinding?.bannerArticle?.adapter = object : BannerImageAdapter<BannerData>(data) {
                    override fun onBindView(
                        holder: BannerImageHolder?,
                        data: BannerData?,
                        position: Int,
                        size: Int
                    ) {
                        holder!!.imageView.load(data?.imagePath){
                            placeholder(R.mipmap.img_placeholder)
                        }

                    }
                }
            }

            override fun onReload(v: View?) {
            }
        })

        //请求首页文章列表
        lifecycleScope.launch {
            mViewModel.articlePagingFlow().collectLatest { data ->
                mArticlePagingAdapter.submitData(data)
            }
        }

    }

    private fun initListener() {

        mBinding?.rvHomeArticle?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })
        //下拉刷洗
//        mBinding?.swipeLayout?.setOnRefreshListener {
//            Log.d(TAG, "initListener: refresh")
//            mArticlePagingAdapter.refresh()
//        }

        //监听paging数据刷新状态
        lifecycleScope.launchWhenCreated {
            mArticlePagingAdapter.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) {
                    //mBinding?.swipeLayout?.isRefreshing = false
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_article
    }
}
