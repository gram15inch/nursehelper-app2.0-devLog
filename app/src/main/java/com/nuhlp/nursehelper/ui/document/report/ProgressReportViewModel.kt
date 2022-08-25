package com.nuhlp.nursehelper.ui.document.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nuhlp.nursehelper.NurseHelperApplication
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.datasource.room.app.Document
import com.nuhlp.nursehelper.datasource.room.app.Patient
import com.nuhlp.nursehelper.datasource.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
/*
todo 8/25
 title 추가
 sfb 일반버튼으로변경
0. documentLive null값 해결
1. sfb 저장/불러오기 구현
2. wfb 호출시 프래그먼트/view 중 선택
3. wfb 구현
4. contentsJs 해결*/

class ProgressReportViewModel : ViewModel() {
    private val appRepository = AppRepository(getAppDatabase(NurseHelperApplication.context()))

    private val _patient= MutableSharedFlow<Patient>()
    val patient get() :Flow<Patient> = _patient
    private val _document= MutableSharedFlow<Document>()
    val document get() :Flow<Document> = _document
    val documentLiveData  = _document.asLiveData()
    private val _businessPlace= MutableSharedFlow<BusinessPlace>()
    val businessPlace get() :Flow<BusinessPlace> = _businessPlace
    val isChanged = MutableStateFlow(false)


    fun refreshDocument(docNo :Int){
        viewModelScope.launch{ _document.emit(appRepository.getDocument(docNo)) }
    }
    fun refreshPatient(patNo :Int){
        viewModelScope.launch{ _patient.emit(appRepository.getPatient(patNo)) }
    }
    fun refreshPlace(bpNo :Int){
        viewModelScope.launch{ _businessPlace.emit(appRepository.getBusinessPlace(bpNo)) }
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProgressReportViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProgressReportViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}