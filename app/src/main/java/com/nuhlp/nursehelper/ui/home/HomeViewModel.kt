package com.nuhlp.nursehelper.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.nuhlp.nursehelper.NurseHelperApplication
import com.nuhlp.nursehelper.datasource.room.app.*
import com.nuhlp.nursehelper.repository.AppRepository
import com.nuhlp.nursehelper.utill.base.map.BaseMapViewModel
import com.nuhlp.nursehelper.utill.useapp.AppTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel :BaseMapViewModel() {
    private val appRepository = AppRepository(getAppDatabase(NurseHelperApplication.context()))

    private val _patients   = MutableSharedFlow<List<Patient>>()
    private val _patient    = MutableSharedFlow<Patient>()
    private val _docCountPM = MutableSharedFlow<List<Int>>()
    private val _docPM      = MutableSharedFlow<List<Document>>()
    val patients    : Flow<List<Patient>> = _patients
    val patient     : Flow<Patient> = _patient
    val docCountPM  : Flow<List<Int>> = _docCountPM
    val docPM       : Flow<List<Document>> = _docPM


    /* patients */

    fun updatePatients(bpNo:Int) = viewModelScope.launch{
        _patients.emit(appRepository.getPatientsWithBpNo(bpNo))
    }
    fun updatePatient(p: Patient) = viewModelScope.launch{
        _patient.emit(p)
    }


    /* Documents */

    fun updateDocCountPerMonth(pNo:Int)= viewModelScope.launch {
        _docCountPM.emit(appRepository.getDocCountPM(pNo).toInt()) // dataCount[0,3,0,7,9] -> month[2,4,5]
    }

    fun updateDocInMonth(m: Int, pNo: Int) = viewModelScope.launch {
       Log.d("HomeFragment","updateDocInMonth!! : ${pNo}")
        _docPM.emit(appRepository.getDocWithM(String.format("%02d",m),pNo))
    }

    fun setDoc(doc: Document) = CoroutineScope(Dispatchers.IO).launch{
        appRepository.setDocument(doc)
    }


    /* 초기셋팅 */
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
    fun setCareService(careService: CareService){
        viewModelScope.launch {
            appRepository.setCareService(careService)
        }
    }


    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

    fun docToIndex(docList: List<Document>): List<Int> {
        val list = mutableListOf<Int>()
        val cal = java.util.Calendar.getInstance()
        docList.forEach { doc->
            doc.crtDate.apply {
                cal.time = AppTime.SDF.parse(this)
                list.add(cal.get(java.util.Calendar.DAY_OF_MONTH))
            }
        }
        return list
    }

   /* **** 사용하지않음 **** */

    // 중복호출 문제발생
    fun setObserver()  {
        /*  viewModelScope.launch {
              patients.collect(){

                  Log.d("HomeFragment","patients update!! size:${it.size}")
                  if(it.isNotEmpty()){
                      val pat = it.first()
                      updatePatient(pat)
                  }

                  if(it.isEmpty())
                  {
                      _livePatAdapter.submitList(emptyList())
                      _liveDocAdapter.submitList(emptyList())
                  }else {
                      _livePatAdapter.submitList(it)
                      val pat = _homeViewModel.patients.value?.first()
                      if (pat != null)
                          _homeViewModel.updatePatient(pat)
                  }
              }
          }

          viewModelScope.launch {
              patient.collect(){ pat-> //2번호출
                  Log.d("HomeFragment", "patientItem Update!! ${pat.name}")
                  updateDocCountPerMonth(pat.patNo)
              }
          }
          viewModelScope.launch {
              docCountPM.collect(){ list -> //3번호출
                  Log.d("HomeFragment", "docCountPM Update!! size: ${list.size}")
                  if (list.isNotEmpty()){
                      updateDocInMonth(list.last())
                  }
                  if (list.isNotEmpty()){
                      index_recyclerView.updateIndex(list, false)
                      recyclerViewUpdate(list.last(), false)
                  }else
                      _liveDocAdapter.submitList(emptyList())

              }
          }
          viewModelScope.launch {
              docPM.collect() { //4번호출
                  Log.d("HomeFragment", "docPM Update!! size: ${it.size}")
                  if(it.isNotEmpty()){
                      // 이후 초기화
                  }
                  if(it.isEmpty()) {
                      binding.indexRecyclerView.updateIndex(emptyList(), true)
                      _liveDocAdapter.submitList(emptyList())
                  }
                  else {
                      binding.indexRecyclerView.updateIndex(docToIndex(it), true)
                      _liveDocAdapter.submitList(it)
                  }
              }
          }*/
    }

    fun deleteAllDoc()= CoroutineScope(Dispatchers.IO).launch{
        appRepository.deleteAll()
    }
}


