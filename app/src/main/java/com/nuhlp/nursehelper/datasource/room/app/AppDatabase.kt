package com.nuhlp.nursehelper.datasource.room.app

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(version =1,
    entities = [Document::class,Patient::class,Product::class,Stock::class,BusinessPlace::class,CareService::class]
,/*autoMigrations = [
        AutoMigration (from = 2, to = 3,spec = AppDatabase.DeleteAutoCareService::class)
    ]*/
)
abstract class AppDatabase: RoomDatabase() {
    @DeleteTable(tableName = "CareService")
    class DeleteAutoCareService : AutoMigrationSpec
    abstract val appDao: AppDao
}

private lateinit var INSTANCE: AppDatabase

fun getAppDatabase(context: Context,databaseName: String = "nuhlpDB"): AppDatabase {

    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                databaseName) // .createFromAsset("database/bus_schedule.db").addMigrations(MIGRATION_1_2())
                .build()
        }

    }
    return INSTANCE
}
