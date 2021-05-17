package com.fuusy.jetpackkt

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.fuusy.common.base.BaseActivity
import com.fuusy.common.ktx.setupWithNavController
import com.fuusy.common.support.Constants
import com.fuusy.jetpackkt.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

@Route(path = Constants.PATH_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentNavController: LiveData<NavController>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

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
        controller?.observe(this, Observer { navController ->
            //setupActionBarWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
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