package com.nuhlp.nursehelper.repository

import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.data.room.app.DataCount
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.utill.useapp.AppProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AppRepository(private val AppDB: AppDatabase) {
    var pNo = 0
    var monthList = AppDB.appDao.getCountMFlow("2022%",pNo)

    fun getDocWithM(m:String):List<Document>{
      return AppDB.appDao.getDoc("2022-$m%",pNo)
    }

    fun getDocCountPM(pNo:Int):List<DataCount>{
        return AppDB.appDao.getCountPerMonth("2022%",pNo)
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