package com.fuusy.personal.ui

import android.os.Bundle
import com.fuusy.common.base.BaseActivity
import com.fuusy.personal.R
import com.fuusy.personal.databinding.ActivityPersonalBinding

class PersonalActivity : BaseActivity<ActivityPersonalBinding>() {


    override fun getLayoutId(): Int =
        R.layout.activity_personal

    override fun initData(savedInstanceState: Bundle?) {

    }
}
