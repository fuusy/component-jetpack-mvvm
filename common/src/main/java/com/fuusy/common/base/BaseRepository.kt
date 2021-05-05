package com.fuusy.common.base

import android.util.Log
import com.fuusy.common.network.BaseResp
import com.fuusy.common.network.ResState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException


/**
 *
 */
private const val TAG = "BaseRepository"
open class BaseRepository() {

    suspend fun <T : Any> executeResp(
        resp: BaseResp<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): ResState<T> {
        return coroutineScope {
            if (resp.errorCode == 0) {
                successBlock?.let { it() }
                ResState.Success(resp.data)
            } else {
                Log.d(TAG, "executeResp: error")
                errorBlock?.let { it() }
                ResState.Error(IOException(resp.errorMsg))
            }
        }
    }

}