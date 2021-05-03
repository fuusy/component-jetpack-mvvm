package com.fuusy.common.support

import android.view.MenuItem
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 *BottomNavigationView和ViewPager2关联
 */
class BnvMediator(
    private val bnv: BottomNavigationView,
    private val vp2: ViewPager2,
    private val config: ((bnv: BottomNavigationView, vp2: ViewPager2) -> Unit)? = null

) {

    private val map = mutableMapOf<MenuItem, Int>()

    init {
        bnv.menu.forEachIndexed { index, item ->
            map[item] = index
        }
    }

    fun attach() {

        config?.let { it(bnv, vp2) }
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnv.selectedItemId = bnv.menu[position].itemId
            }
        })
        bnv.setOnNavigationItemSelectedListener { item ->
            vp2.currentItem = map[item] ?: error("没有对应${item.title}的ViewPager2的元素")
            true
        }
    }

}