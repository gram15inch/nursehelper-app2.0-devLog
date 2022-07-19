package com.nuhlp.nursehelper.data.room.app

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(version =2,
    entities = [Document::class,Patient::class,Product::class,Stock::class])
abstract class AppDatabase: RoomDatabase() {
    abstract val appDao: AppDao
}

private lateinit var INSTANCE: AppDatabase

fun getAppDatabase(context: Context,databaseName: String = "nuhlpDB"): AppDatabase {
    fun MIGRATION_1_2() = Migration(1, 2) {
        @Override
        fun migrate(a_database: SupportSQLiteDatabase) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    }
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                databaseName) // .createFromAsset("database/bus_schedule.db")
                .addMigrations(MIGRATION_1_2())
                .build()
        }

    }
    return INSTANCE
}
