package com.nuhlp.nursehelper.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("select Count(*) from UserAccount where id=:userId ")
    fun getAvailableId(userId:String): Flow<Int>


    // crud

    @Query("select * from UserAccount where id =:userId")
    fun getUser(userId: String):UserAccount

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(user: UserAccount)

    @Update
    fun updateUser(user: UserAccount)

    @Query("select Count(*) FROM UserAccount where id=:id and pw=:pw")
    fun countExistedUser(id: String, pw: String):Int


    // test 전용

    @Query("select * from UserAccount")
    fun getAll(): List<UserAccount>

    @Query("DELETE FROM UserAccount")
    fun deleteAll()


}
