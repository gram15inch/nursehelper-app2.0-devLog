package com.nuhlp.nursehelper.datasource.room.user

import android.content.Context
import androidx.room.*


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
                databaseName) // .createFromAsset("database/bus_schedule.db")
                .build()
        }

    }
    return INSTANCE
}
