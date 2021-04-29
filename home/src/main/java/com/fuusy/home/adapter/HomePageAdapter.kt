package com.fuusy.home.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fuusy.home.ui.ArticleFragment
import com.fuusy.home.ui.DailyQuestionFragment
import com.fuusy.home.ui.SquareFragment

private const val TAG = "HomePageAdapter"

class HomePageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment: $position")
        return when (position) {
            0 -> DailyQuestionFragment()
            1 -> ArticleFragment()
            else -> SquareFragment()
        }
    }
}