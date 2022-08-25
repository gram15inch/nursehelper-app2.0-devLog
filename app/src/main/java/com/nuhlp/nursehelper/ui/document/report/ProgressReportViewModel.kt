package com.nuhlp.nursehelper.ui.document.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nuhlp.nursehelper.NurseHelperApplication
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.datasource.room.app.Document
import com.nuhlp.nursehelper.datasource.room.app.Patient
import com.nuhlp.nursehelper.datasource.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch



class ProgressReportViewModel : ViewModel() {
    private val appRepository = AppRepository(getAppDatabase(NurseHelperApplication.context()))

    private val _patient= MutableSharedFlow<Patient>()
    val patient = _patient
    private val _document= MutableSharedFlow<Document>()
    val document = _document
    private val _businessPlace= MutableSharedFlow<BusinessPlace>()
    val businessPlace = _businessPlace
    val isChanged = MutableStateFlow(false)

    fun updateDocument(docNo :Int){
        viewModelScope.launch{ _document.emit(appRepository.getDocument(docNo)) }
    }
    fun updatePatient(patNo :Int){
        viewModelScope.launch{ _patient.emit(appRepository.getPatient(patNo)) }
    }
    fun updatePlace(bpNo :Int){
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