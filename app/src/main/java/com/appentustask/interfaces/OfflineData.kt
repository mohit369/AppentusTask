package com.appentustask.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appentustask.model.ResponseGetModel
import com.appentustask.model.ResponseGetModelItem
import kotlinx.coroutines.flow.Flow


/**
 * database query interface
 */

@Dao
interface OfflineData {

    @Query("SELECT * FROM offlineList")
    fun fetchData(): List<ResponseGetModelItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertnewdata(itemList: List<ResponseGetModelItem>)

    @Query("DELETE FROM offlineList")
     fun deleteAllData()

}