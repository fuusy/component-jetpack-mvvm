package com.fuusy.home.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

import com.fuusy.common.base.BaseFragment
import com.fuusy.common.network.net.IStateObserver
import com.fuusy.common.widget.FooterAdapter
import com.fuusy.home.R
import com.fuusy.home.adapter.paging.ArticleMultiPagingAdapter
import com.fuusy.home.bean.BannerData
import com.fuusy.home.databinding.FragmentArticleBinding
import com.fuusy.home.viewmodel.ArticleViewModel
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
        ArticleMultiPagingAdapter()

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
                data?.let {
                    mArticlePagingAdapter.addBannerList(it)
                    mArticlePagingAdapter.notifyItemChanged(0)
                }
            }

            override fun onReload(v: View?) {
            }
        })

        //请求首页文章列表
        lifecycleScope.launchWhenCreated {
            mViewModel.articlePagingFlow().collectLatest { data ->
                mArticlePagingAdapter.submitData(data)
            }
        }

    }

    private fun initListener() {

        //下拉刷新
        mBinding?.swipeLayout?.setOnRefreshListener {
            mViewModel.loadBanner()
            mArticlePagingAdapter.refresh()
        }

        //监听paging数据刷新状态
        lifecycleScope.launchWhenCreated {
            mArticlePagingAdapter.loadStateFlow.collectLatest {
                Log.d(TAG, "initListener: $it")
                mBinding?.swipeLayout?.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_article
    }
}
