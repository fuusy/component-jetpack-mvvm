package com.fuusy.common.network.net

import androidx.lifecycle.MutableLiveData
import com.fuusy.common.network.BaseResp


/**
 * @date：2021/6/11
 * @author fuusy
 * @instruction：MutableLiveData,用于将请求状态分发给UI
 */
class StateLiveData<T> : MutableLiveData<BaseResp<T>>() {
}