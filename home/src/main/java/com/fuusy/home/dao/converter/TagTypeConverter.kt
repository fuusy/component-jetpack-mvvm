package com.fuusy.home.dao.converter

import androidx.room.TypeConverter
import com.fuusy.home.bean.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction： List<Tag>的类型转换
 */
class TagTypeConverter {
    @TypeConverter
    fun stringToObject(value: String): List<Tag> {
        val listType = object : TypeToken<List<Tag>>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<Tag>): String {
        return Gson().toJson(list)
    }
}