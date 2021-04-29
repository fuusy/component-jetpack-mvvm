package com.fuusy.common.support

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T> : MutableLiveData<T>() {


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            observer.onChanged(it)
        })
    }

    override fun postValue(value: T) {
        super.postValue(value)

    }
}