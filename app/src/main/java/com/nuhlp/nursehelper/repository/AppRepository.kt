package com.nuhlp.nursehelper.repository

import androidx.lifecycle.MutableLiveData
import com.nuhlp.nursehelper.datasource.room.app.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AppRepository(private val AppDB: AppDatabase) {
    var pNo = 1
    var monthList = AppDB.appDao.getCountMFlow("2022%",pNo)

    fun getDocWithM(m:String):List<Document>{
      return AppDB.appDao.getDoc("2022-$m%",pNo)
    }

   fun getDocCountPM(pNo:Int):List<DataCount>{
        return AppDB.appDao.getCountPerMonth("2022%",pNo)
    }

    suspend fun setBusinessPlace(businessPlace: BusinessPlace)= withContext(Dispatchers.IO) {
        AppDB.appDao.setBP(businessPlace)
    }

    suspend fun getDocument(docNo: Int) :Document = withContext(Dispatchers.IO) {
        AppDB.appDao.getDoc(docNo,1)
    }
    suspend fun setDocument(doc: Document) = withContext(Dispatchers.IO) {
        AppDB.appDao.setDoc(doc)
    }
    suspend fun deleteAll(){
        AppDB.appDao.deleteAll()
    }

    suspend fun setPatient(patient: Patient) = withContext(Dispatchers.IO) {
        AppDB.appDao.setPt(patient)
    }

   suspend fun getPatientsWithBpNo(bpNo: Int): List<Patient> = withContext(Dispatchers.IO){
       return@withContext AppDB.appDao.getPatients(bpNo = bpNo.toString())
    }

}