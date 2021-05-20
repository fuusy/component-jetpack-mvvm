package com.fuusy.home.bean

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM tab_remote_key WHERE articleId = :articleId AND articleType =:articleType")
    suspend fun remoteKeysArticleId(articleId: Int, articleType: Int): RemoteKey?

    @Query("DELETE FROM tab_remote_key WHERE articleType =:articleType")
    suspend fun clearRemoteKeys(articleType: Int)
}