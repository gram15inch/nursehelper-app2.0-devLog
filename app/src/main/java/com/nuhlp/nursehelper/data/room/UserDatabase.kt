package com.nuhlp.nursehelper.data.room

import android.content.Context
import androidx.room.*

@Dao
interface UserDao {
    @Query("select Count(*) from UserAccount where id=:userId ")
    fun checkId(userId:String):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(user: UserAccount)

    @Query("select * from UserAccount where id =:userId")
    fun getUser(userId: String):UserAccount

    @Query("select * from UserAccount")
    fun getAll(): List<UserAccount>

    @Query("DELETE FROM UserAccount")
    fun deleteAll()

   /* @Query("update sqlite_sequence set seq=0 where name='SequenceAction'")
    fun resetAccountNo()*/

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<DatabaseVideo>)*/

}



@Database(entities = [UserAccount::class], version =1)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: UserDatabase

fun getUserDatabase(context: Context,databaseName: String = "users"): UserDatabase {
    synchronized(UserDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java,
                databaseName).build()
        }

    }
    return INSTANCE
}
