package com.fuusy.common.loadsir

import com.fuusy.common.R
import com.kingja.loadsir.callback.Callback

class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.base_layout_error
    }
}