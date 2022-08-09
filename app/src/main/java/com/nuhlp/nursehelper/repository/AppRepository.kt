package com.nuhlp.nursehelper.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.nursehelper.data.network.MapsApiService
import com.nuhlp.nursehelper.data.network.model.place.Place
import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.data.room.app.DataCount
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.utill.useapp.AppProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class AppRepository(private val AppDB: AppDatabase, private val AppNetwork:MapsApiService) {
    var pNo = 0
    var monthList = AppDB.appDao.getCountMFlow("2022%",pNo)

    suspend fun testGetPlace(latLng:LatLng): List<Place> {
        return AppNetwork.getPlaces(
                "HP8",
            latLng.latitude, latLng.longitude
        ).places
    }

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