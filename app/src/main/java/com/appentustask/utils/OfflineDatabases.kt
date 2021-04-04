package com.appentustask.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appentustask.interfaces.OfflineData
import com.appentustask.model.ResponseGetModel
import com.appentustask.model.ResponseGetModelItem

/**
 * database instance is created with RoomDb
 * define offline data interface in abstract method to use it later
 */

@Database(entities = [ResponseGetModelItem::class],version = 1,exportSchema = false)
abstract class OfflineDatabases: RoomDatabase() {

    abstract fun offlineData():OfflineData

    companion object {

        private var appDatabase: OfflineDatabases? = null


        fun getInstance(context: Context): OfflineDatabases {
            if (appDatabase == null) {
                appDatabase =
                    Room.databaseBuilder(
                        context.applicationContext,
                        OfflineDatabases::class.java,
                        "offlinedatabase"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return appDatabase!!
        }
    }

}