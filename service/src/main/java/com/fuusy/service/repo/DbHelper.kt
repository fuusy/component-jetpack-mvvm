package com.fuusy.service.repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DbHelper {
    /**
     * 获取room数据表中存储的userInfo
     * return liveData形式
     */
    fun getLiveUserInfo(context: Context) =
        UserDB.get(context).userDao.queryLiveUser()

    /**
     * 以普通数据对象的形式，获取userInfo
     */
    fun getUserInfo(context: Context) = UserDB.get(context).userDao.queryUser()

    /**
     * 删除数据表中的userInfo信息
     */
    fun deleteUserInfo(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            getUserInfo(context)?.let { info ->
                UserDB.get(context).userDao.deleteUser(info)
            }
        }
    }

    /**
     * 新增用户数据到数据表
     */
    fun insertUserInfo(context: Context, user: LoginResp) {
        GlobalScope.launch(Dispatchers.IO) {
            UserDB.get(context).userDao.insertUser(user)
        }
    }
}