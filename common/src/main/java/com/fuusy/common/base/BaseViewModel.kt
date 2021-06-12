package com.fuusy.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuusy.common.support.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @date：2021/6/11
 * @author fuusy
 * @instruction：
 */
open class BaseViewModel : ViewModel() {
     val loadingLiveData = SingleLiveData<Boolean>()

     val errorLiveData = SingleLiveData<Throwable>()

     /**
      * @deprecated
      */
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
                    error(e)
               } finally {
                    complete()
               }
          }
     }



}