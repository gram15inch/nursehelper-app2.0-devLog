package com.nuhlp.nursehelper.utill.base.recyclerview

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nuhlp.nursehelper.datasource.room.app.*
import com.nuhlp.nursehelper.repository.AppRepository
import com.nuhlp.nursehelper.utill.base.map.BaseMapViewModel
import com.nuhlp.nursehelper.utill.test.DummyDataUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseRecyclerViewModel(application:Application):BaseMapViewModel(application) {

    private val _patients = MutableLiveData<List<Patient>>()
    val patients: LiveData<List<Patient>> =  _patients
    private val _docCountPM = MutableLiveData<List<Int>>()
    val docCountPM = _docCountPM
    private val _docPM = MutableLiveData<List<Document>>()
    val docPM = _docPM
    private val appRepository = AppRepository(getAppDatabase(application))

    var STATE_FIRST = true


    // todo 옵저버 주체 바꾸기위해 임시생성
    var patientNoLive = MutableLiveData<Int>()

    /* patients */

    fun updatePatients(bpNo:Int) = viewModelScope.launch{
        Log.d("HomeFragment","patients update!!")
        _patients.value = appRepository.getPatientsWithBpNo(bpNo)
    }




    /* Documents */

    fun getCountPerMonth(pNo:Int)= viewModelScope.launch {
            _docCountPM.value= appRepository.getDocCountPM(pNo).toInt() // dataCount[0,3,0,7,9] -> month[2,4,5]
        }

    fun setDoc(doc: Document) = CoroutineScope(Dispatchers.IO).launch{
        appRepository.setDocument(doc)
    }
    fun getDocInMonth(m:Int) = viewModelScope.launch {
         docPM.value= appRepository.getDocWithM(String.format("%02d",m) )
    }

    fun selectPatientNo(no:Int){
        patientNoLive.value = no
        appRepository.pNo = no
    }

    fun setBusinessPlace(businessPlace: BusinessPlace){
        viewModelScope.launch{
            appRepository.setBusinessPlace(businessPlace)
        }
    }
    fun setPatient(patient: Patient){
        viewModelScope.launch{
            appRepository.setPatient(patient)
        }
    }

    // test
    fun test(){
        /*STATE_FIRST = true
        selectPatientNo((patientNoLive.value!! + 1)%2)*/
        businessPlace.value = DummyDataUtil.placeList[1]
    }
    fun deleteAllDoc()=CoroutineScope(Dispatchers.IO).launch{
        appRepository.deleteAll()
    }
}
