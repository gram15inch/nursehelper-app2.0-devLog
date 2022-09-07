package com.nuhlp.nursehelper.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nuhlp.nursehelper.datasource.room.app.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AppRepository(private val AppDB: AppDatabase) {

    suspend fun getDocWithM(m: String, pNo: Int):List<Document> = withContext(Dispatchers.IO) {
        return@withContext  AppDB.appDao.getDoc("2022-$m%", pNo)
    }

   suspend fun getDocCountPM(pNo:Int):List<DataCount> = withContext(Dispatchers.IO) {
        return@withContext AppDB.appDao.getCountPerMonth("2022%",pNo)
    }

    suspend fun setBusinessPlace(businessPlace: BusinessPlace)= withContext(Dispatchers.IO) {
        AppDB.appDao.setBP(businessPlace)
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

   suspend fun setCareService(careService: CareService) = withContext(Dispatchers.IO) {
        AppDB.appDao.setCS(careService)
    }

   suspend fun getPatientsWithBpNo(bpNo: Int): List<Patient> = withContext(Dispatchers.IO){
       return@withContext AppDB.appDao.getPatients(bpNo = bpNo.toString())
    }
    /* get */
    suspend fun getDocument(docNo: Int) :Document = withContext(Dispatchers.IO) {
        AppDB.appDao.getDoc(docNo)
    }
    suspend fun getPatient(patientNo: Int): Patient = withContext(Dispatchers.IO){
       return@withContext AppDB.appDao.getPatient(patientNo.toString())
    }
    suspend fun getBusinessPlace(bpNo: Int): BusinessPlace = withContext(Dispatchers.IO){
        return@withContext AppDB.appDao.getBusinessPlace(bpNo.toString())
    }




}