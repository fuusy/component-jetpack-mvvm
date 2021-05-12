package com.fuusy.common.network.net

import androidx.lifecycle.MutableLiveData
import com.fuusy.common.network.BaseResp


/**
 * MutableLiveData,用于将请求状态分发给UI
 */
class StateLiveData<T> : MutableLiveData<BaseResp<T>>() {
}