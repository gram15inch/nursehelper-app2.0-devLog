package com.nuhlp.nursehelper.ui.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nuhlp.nursehelper.NurseHelperApplication
import com.nuhlp.nursehelper.datasource.room.app.Document
import com.nuhlp.nursehelper.datasource.room.app.Patient
import com.nuhlp.nursehelper.datasource.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository
import com.nuhlp.nursehelper.ui.home.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class DocumentViewModel :ViewModel() {
    private val appRepository = AppRepository(getAppDatabase(NurseHelperApplication.context()))

    private val _patient= MutableSharedFlow<Patient>()
    val patient = _patient
    private val _document= MutableSharedFlow<Document>()
    val document = _document

    fun updateDocument(docNo :Int){
        viewModelScope.launch{ _document.emit(appRepository.getDocument(docNo)) }
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DocumentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DocumentViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}