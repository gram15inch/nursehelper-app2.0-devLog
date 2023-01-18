package com.nuhlp.nursehelper

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.nuhlp.nursehelper.datasource.room.app.AppDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class  NurseHelperApplication : Application() {

    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */

    companion object {
        lateinit var instance: NurseHelperApplication
            private set

        fun context() : Context {
            return instance.applicationContext
        }

    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    private lateinit var appDatabase: AppDatabase
    fun getAppDatabase2(context: Context,databaseName: String = "nuhlpDB"): AppDatabase {

        synchronized(AppDatabase::class.java) {
            if (!::appDatabase.isInitialized) {
                appDatabase = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    databaseName) // .createFromAsset("database/bus_schedule.db").addMigrations(MIGRATION_1_2())
                    .build()
            }

        }
        return appDatabase
    }


}