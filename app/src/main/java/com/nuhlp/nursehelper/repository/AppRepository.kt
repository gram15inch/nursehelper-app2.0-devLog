package com.nuhlp.nursehelper.repository

import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.data.room.app.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val AppDB: AppDatabase) {

    suspend fun getDocument(docNo: Int) :Document = withContext(Dispatchers.IO) {
        AppDB.appDao.getDoc(docNo)
    }
    suspend fun setDocument(doc: Document) = withContext(Dispatchers.IO) {
        AppDB.appDao.setDoc(doc)
    }

}