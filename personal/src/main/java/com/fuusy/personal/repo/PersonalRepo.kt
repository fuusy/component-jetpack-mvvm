package com.fuusy.personal.repo

import com.fuusy.common.network.BaseRepository
import com.fuusy.common.network.RetrofitManager

class PersonalRepo : BaseRepository() {
    private val service = RetrofitManager.initRetrofit().getService(PersonalApi::class.java)

}