package com.nuhlp.nursehelper.repository

import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.data.room.app.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val AppDB: AppDatabase) {
    var pNo = 0
    val monthList = AppDB.appDao.getCountMFlow("2022%",pNo)

    suspend fun getDocWithM(m:String):List<Document>{
      return AppDB.appDao.getDoc("2022-$m%",pNo)
    }

    suspend fun getDocument(docNo: Int) :Document = withContext(Dispatchers.IO) {
        AppDB.appDao.getDoc(docNo,0)
    }
    suspend fun setDocument(doc: Document) = withContext(Dispatchers.IO) {
        AppDB.appDao.setDoc(doc)
    }
    suspend fun deleteAll(){
        AppDB.appDao.deleteAll()
    }

}