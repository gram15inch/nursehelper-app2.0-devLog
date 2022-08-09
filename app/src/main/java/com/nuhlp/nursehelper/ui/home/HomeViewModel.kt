package com.nuhlp.nursehelper.ui.home

import android.app.Application
import android.icu.util.Calendar
import android.provider.DocumentsContract
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.nursehelper.data.network.KaKaoApi
import com.nuhlp.nursehelper.data.network.getAppKakaoApi
import com.nuhlp.nursehelper.data.network.model.place.Place
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.data.room.app.toInt
import com.nuhlp.nursehelper.repository.AppRepository
import com.nuhlp.nursehelper.utill.base.map.BaseMapViewModel
import com.nuhlp.nursehelper.utill.base.recyclerview.BaseRecyclerViewModel
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel (application: Application) : BaseRecyclerViewModel(application) {


    override val appRepositoryRecyclerView = AppRepository(
        getAppDatabase(application), getAppKakaoApi()
    )
    private val appRepository = appRepositoryRecyclerView


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


   /* **** test **** */
    private var countT = 0
    fun testCall(){
       updatePlaces(Constants.LATLNG_DONGBAEK)
    }
    fun btnClick(){
        test()
    }
    fun deleteAllDoc()=CoroutineScope(Dispatchers.IO).launch{
        appRepository.deleteAll()
    }
    private val roomDummy :List<Document> by lazy{
        val list = mutableListOf<Document>()
        val time = Calendar.getInstance()
        time.set(Calendar.YEAR,2022)
        time.set(Calendar.MONTH,0)
        time.set(Calendar.DAY_OF_MONTH,1)

        repeat(365){
            val t= AppTime.SDF.format(time.time)
            list.add(Document(it,0,0,t,t))
            time.add(Calendar.DAY_OF_MONTH,1)
        }
       list.toList()
    }
}


