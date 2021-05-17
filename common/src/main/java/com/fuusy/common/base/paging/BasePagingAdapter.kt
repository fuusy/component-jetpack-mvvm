package com.fuusy.common.base.paging

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "BasePagingAdapter"

abstract class BasePagingAdapter<T : Any>(private var diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    companion object {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as BasePagingAdapter<*>.BaseViewHolder).bindNormalData(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = BaseViewHolder(parent, viewType)
        holder.itemView.setOnClickListener {
            onItemClick(getItem(holder.layoutPosition))
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return getItemLayout(position)
    }

    /**
     * 子类获取layout
     */
    protected abstract fun getItemLayout(position: Int): Int

    /**
     * itemView的点击事件，子类实现
     */
    protected abstract fun onItemClick(data: T?)

    /**
     * 子类绑定数据
     */
    protected abstract fun bindData(helper: ItemHelper, data: T?)


    inner class BaseViewHolder(parent: ViewGroup, layout: Int) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false)
    ) {
        private val helper: ItemHelper = ItemHelper(this)

        fun bindNormalData(item: Any?) {
            bindData(helper, item as T)
        }
    }


    /**
     * ItemView的辅助类
     */
    class ItemHelper(private val holder: BasePagingAdapter<*>.BaseViewHolder) {
        private val itemView = holder.itemView
        private val viewCache = SparseArray<View>()

        private fun findViewById(viewId: Int): View {
            var view = viewCache.get(viewId)
            if (view == null) {
                view = itemView.findViewById(viewId)
                if (view == null) {
                    throw NullPointerException("$viewId can not find this item！")
                }
                viewCache.put(viewId, view)
            }
            return view
        }

        /**
         * TextView设置内容
         */
        fun setText(viewId: Int, text: CharSequence?): ItemHelper {
            (findViewById(viewId) as TextView).text = text
            return this
        }

        /**
         * Glide加载图片
         */
        fun bindImgGlide(viewId: Int, url: String) {
            val imageView: ImageView = findViewById(viewId) as ImageView
            Glide.with(itemView)
                .load(url)
                .into(imageView)
        }
    }


}