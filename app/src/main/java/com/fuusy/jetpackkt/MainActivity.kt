package com.fuusy.jetpackkt

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.fuusy.common.base.BaseActivity
import com.fuusy.common.ktx.setupWithNavController
import com.fuusy.common.support.Constants
import com.fuusy.home.ui.HomeFragment
import com.fuusy.jetpackkt.databinding.ActivityMainBinding
import com.fuusy.personal.ui.PersonalFragment
import com.fuusy.project.ui.ProjectFragment

private const val TAG = "MainActivity"

@Route(path = Constants.PATH_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentNavController: LiveData<NavController>? = null

    companion object {
        private const val INDEX_HOME = 0
        private const val INDEX_PROJECT = 1
        private const val INDEX_PERSONAL = 2
    }

    private val fragments = mapOf<Int, Fragment>(
        INDEX_HOME to HomeFragment(),
        INDEX_PROJECT to ProjectFragment(),
        INDEX_PERSONAL to PersonalFragment()
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private lateinit var mFragmentAdapter: VpFragmentAdapter

    override fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

//        mFragmentAdapter = VpFragmentAdapter(this, fragments)
//        mBinding?.run {
//            BnvMediator(navView,vpFragment){bnv,vp2->
//                vp2.isUserInputEnabled = false
//            }.attach()
//        }
//        mBinding?.vpFragment?.adapter = mFragmentAdapter
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    class VpFragmentAdapter(fragment: FragmentActivity, private val fragments: Map<Int, Fragment>) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position] ?: error("ViewPager接收参数index越界")

        }
    }


    /**
     * navigation绑定BottomNavigationView
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(R.navigation.navi_home, R.navigation.navi_project, R.navigation.navi_personal)

        mBinding.run {
            Log.d(TAG, "setupBottomNavigationBar: ")
        }
        val controller = mBinding?.navView?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        controller?.observe(this, Observer { navController ->
            //setupActionBarWithNavController(navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                run {
                    val id = destination.id
                    if (id == R.id.projectContentFragment) {
                        mBinding?.navView?.visibility = View.GONE
                    } else {
                        mBinding?.navView?.visibility = View.VISIBLE
                    }
                }
            }
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}