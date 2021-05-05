package com.fuusy.home.ui

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fuusy.common.base.BaseFragment
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

private const val TAG = "ArticleFragment"

/**
 * 首页Fragment
 */
class ArticleFragment : BaseFragment<FragmentArticleBinding, ArticleViewModel>() {

    private var mArticlePagingAdapter =
        HomeArticlePagingAdapter()

    override fun initData() {
        Log.d(TAG, "initData: ")
        initListener()

        mBinding?.rvHomeArticle?.adapter = mArticlePagingAdapter.withLoadStateFooter(
            FooterAdapter {
                Log.d(TAG, "initData: retry")
                mArticlePagingAdapter.retry()
            })


        mViewModel?.getBanner()
        mViewModel?.bannerLiveData?.observe(this, Observer {

            showSuccess()
            //绑定banner
            mBinding?.bannerArticle?.adapter = object : BannerImageAdapter<BannerData>(it) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerData?,
                    position: Int,
                    size: Int
                ) {
                    Glide.with(this@ArticleFragment)
                        .load(data?.imagePath)
                        .into(holder!!.imageView)
                }
            }
        })

        lifecycleScope.launch {
            //请求首页文章列表
            mViewModel?.articlePagingFlow()?.collectLatest { data ->
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

    override fun getViewModel(): ArticleViewModel =
        ViewModelProviders.of(this).get(ArticleViewModel::class.java)

}
