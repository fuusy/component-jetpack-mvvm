package com.fuusy.common.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuusy.common.network.BaseResp
import com.fuusy.common.network.DataState
import com.fuusy.common.support.SingleLiveData
import kotlinx.coroutines.*
import java.lang.Exception

private const val TAG = "BaseViewModel"
open class BaseViewModel : ViewModel() {
     val loadingLiveData = SingleLiveData<Boolean>()

     val errorLiveData = SingleLiveData<Throwable>()

     fun launch(
          block: suspend () -> Unit,
          error: suspend (Throwable) -> Unit,
          complete: suspend () -> Unit
     ) {
          loadingLiveData.postValue(true)
          viewModelScope.launch(Dispatchers.IO) {
               try {
                    block()
               } catch (e: Exception) {
                    Log.d(TAG, "launch: error ")
                    error(e)
               } finally {
                    complete()
               }
          }
     }



}