package com.fuusy.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuusy.common.network.BaseResp
import kotlinx.coroutines.*
import java.lang.Exception

open class BaseViewModel : ViewModel() {
     val loadingLiveData = MutableLiveData<Boolean>()

     val errorLiveData = MutableLiveData<Throwable>()


     fun launch(
          block: suspend () -> Unit,
          error: suspend (Throwable) -> Unit,
          complete: suspend () -> Unit
     ) {
          loadingLiveData.postValue(true)
          viewModelScope.launch(Dispatchers.IO) {
               try {
                    block()
               } catch (e: Throwable) {
                    error(e)
               } finally {
                    complete()
               }
          }
     }


}