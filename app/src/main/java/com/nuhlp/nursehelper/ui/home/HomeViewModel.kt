package com.nuhlp.nursehelper.ui.home

import android.app.Application
import android.icu.util.Calendar
import android.provider.DocumentsContract
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository
import com.nuhlp.nursehelper.utill.useapp.AppTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    var STATE_FIRST = true
    private val appRepository = AppRepository(getAppDatabase(application))
    var dayOfMonthCountLive = appRepository.monthList.asLiveData()
    var dayOfMonthDocument = MutableLiveData<List<Document>>()
    var patientNoLive = MutableLiveData<Int>()

    fun setDoc(doc:Document) = CoroutineScope(Dispatchers.IO).launch{
        appRepository.setDocument(doc)
    }

  suspend fun getDocInMonth(m:Int):List<Document>{ return appRepository.getDocWithM(String.format("%02d",m) ) }

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
    fun btnClick(){
        if(appRepository.pNo == 1)
            appRepository.pNo = 0
        else
            appRepository.pNo = 1
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


