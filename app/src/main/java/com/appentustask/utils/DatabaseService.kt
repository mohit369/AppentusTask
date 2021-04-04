package com.appentustask.utils

import android.content.Context
import com.appentustask.interfaces.OfflineData
import com.appentustask.model.ResponseGetModelItem

/**
 * to do operation on Room db
 */

class DatabaseService(context: Context) : OfflineData {

    private val dao: OfflineData = OfflineDatabases.getInstance(context).offlineData()

    override fun fetchData(): List<ResponseGetModelItem> {
        return dao.fetchData()
    }

    override  fun insertnewdata(itemList: List<ResponseGetModelItem>) {
        return dao.insertnewdata(itemList)
    }

    override  fun deleteAllData() {
        dao.deleteAllData()
    }
}