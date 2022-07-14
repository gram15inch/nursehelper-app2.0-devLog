package com.nuhlp.nursehelper.repository

import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.data.room.app.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppRepository(private val AppDB: AppDatabase) {
    val docList = AppDB.appDao.getAllDocFlowList()
    val monthList = AppDB.appDao.getCountMFlow("2022%")

    suspend fun getDocWithM(m:String):List<Document>{
      return AppDB.appDao.getDoc("2022-$m%")
    }

    suspend fun getDocument(docNo: Int) :Document = withContext(Dispatchers.IO) {
        AppDB.appDao.getDoc(docNo)
    }
    suspend fun setDocument(doc: Document) = withContext(Dispatchers.IO) {
        AppDB.appDao.setDoc(doc)
    }
    suspend fun deleteAll(){
        AppDB.appDao.deleteAll()
    }

}