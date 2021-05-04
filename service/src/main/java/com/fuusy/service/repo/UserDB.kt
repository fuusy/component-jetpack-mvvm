package com.fuusy.service.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Database(entities = [LoginResp::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DB_NAME = "tb_user"

        @Volatile
        private var instance: UserDB? = null

        fun get(context: Context): UserDB {
            return instance ?: Room.databaseBuilder(context, UserDB::class.java, DB_NAME)
                .build().also {
                    instance = it
                }
        }
    }
}

@Entity(tableName = "tb_user")
data class LoginResp(
    val admin: Boolean,
    val coinCount: Int,
    val email: String,
    val icon: String,
    @PrimaryKey
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(info: LoginResp)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(info: LoginResp)

    @Delete
    fun deleteUser(info: LoginResp)

    @Query("select * from tb_user where id =:id")
    fun queryLiveUser(id: Int = 0): LiveData<LoginResp>

    @Query("select * from tb_user where id =:id")
    fun queryUser(id: Int = 0): LoginResp?
}