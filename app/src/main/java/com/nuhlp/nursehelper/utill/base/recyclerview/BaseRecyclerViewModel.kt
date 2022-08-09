package com.nuhlp.nursehelper.utill.base.recyclerview

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.data.room.app.toInt
import com.nuhlp.nursehelper.repository.AppRepository
import com.nuhlp.nursehelper.utill.base.map.BaseMapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseRecyclerViewModel(application:Application):BaseMapViewModel(application) {

    abstract val appRepositoryRecyclerView : AppRepository

    var STATE_FIRST = true
    override val appRepositoryMap: AppRepository = appRepositoryRecyclerView
    private val appRepository get() =  appRepositoryRecyclerView

    val dayOfMonthCountLive by lazy { appRepository.monthList.asLiveData() }


    // todo 옵저버 주체 바꾸기위해 임시생성
    var patientNoLive = MutableLiveData<Int>()


    fun getCountPerMonth(pNo:Int):List<Int>{
        return appRepository.getDocCountPM(pNo).toInt() // dataCount[0,3,0,7,9] -> month[2,4,5]
    }
    fun setDoc(doc: Document) = CoroutineScope(Dispatchers.IO).launch{
        appRepository.setDocument(doc)
    }
    fun getDocInMonth(m:Int):List<Document>{ return appRepository.getDocWithM(String.format("%02d",m) ) }
    fun setPatientNo(no:Int){
        patientNoLive.value = no
        appRepository.pNo = no
    }


    // test
    fun test(){
        STATE_FIRST = true
        setPatientNo((patientNoLive.value!! + 1)%2)
    }
}