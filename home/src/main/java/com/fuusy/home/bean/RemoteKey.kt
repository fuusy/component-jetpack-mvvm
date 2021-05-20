package com.fuusy.home.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_remote_key")
data class RemoteKey(
    @PrimaryKey
    val articleId: Int,
    val articleType: Int,
    val prevKey: Int?,
    val nextKey: Int?
)