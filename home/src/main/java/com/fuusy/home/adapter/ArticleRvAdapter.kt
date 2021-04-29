package com.fuusy.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fuusy.common.base.BaseRvAdapter
import com.fuusy.common.base.BaseViewHolder
import com.fuusy.home.R
import com.fuusy.home.bean.BannerData
import com.fuusy.home.databinding.ItemBannerBinding
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import java.util.*

private const val TYPE_BANNER = 0
private const val TYPE_ARTICLE = 1

private const val TAG = "ArticleRvAdapter"
class ArticleRvAdapter : BaseRvAdapter<List<BannerData>>() {

    private var bannerData: List<BannerData> = ArrayList()


    override fun onCreateNormalViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<List<BannerData>> {

        if (viewType == TYPE_BANNER) {
            val binding: ItemBannerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_banner,
                parent, false
            )

            return BannerViewHolder(binding)

        }

        return super.onCreateViewHolder(parent, viewType) as BaseViewHolder<List<BannerData>>
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_BANNER) {
            (holder as BannerViewHolder).bindView(bannerData)
        }

    }

    override fun getNormalItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_BANNER
        }
        return TYPE_ARTICLE
    }

    class BannerViewHolder(private val binding: ItemBannerBinding) : BaseViewHolder<List<BannerData>>(binding.root) {
        override fun bindView(data: List<BannerData>) {
            Log.d(TAG, "bindView: 1")
            binding.bannerArticle.adapter = object :BannerImageAdapter<BannerData>(data){
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerData?,
                    position: Int,
                    size: Int
                ) {

                    Log.d(TAG, "onBindView:${data?.imagePath}")
                    Glide.with(binding.root)
                        .load(data?.imagePath)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(holder!!.imageView)
                }

            }
        }

    }

    fun addBanner(data: List<BannerData>) {
        this.bannerData = data
        Log.d(TAG, "addBanner: ${bannerData.size}")
        notifyItemChanged(0)
    }

}