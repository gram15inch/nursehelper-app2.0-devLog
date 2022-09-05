package com.nuhlp.nursehelper.ui.document.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nuhlp.nursehelper.NurseHelperApplication
import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.datasource.room.app.Document
import com.nuhlp.nursehelper.datasource.room.app.Patient
import com.nuhlp.nursehelper.datasource.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository
import com.nuhlp.nursehelper.utill.arrayToLines
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
/*
todo 8/25
 title 추가
 sfb 일반버튼으로변경
 documentLive null값 해결
 sfb 저장/불러오기 구현
wfb 호출시 프래그먼트/view 중 선택
3. wfb 구현
    - 컨테인뷰안에서 여러 프래그먼트로 입력받음
    -
4. contentsJs 해결
5. place cache 구현*/


class ProgressReportViewModel : ViewModel() {
    private val appRepository = AppRepository(getAppDatabase(NurseHelperApplication.context()))

    private val _patient= MutableSharedFlow<Patient>()
    val patient get() :Flow<Patient> = _patient
    private val _document= MutableStateFlow(Document.empty())
    val document get() :StateFlow<Document> = _document


    private val _businessPlace= MutableSharedFlow<BusinessPlace>()
    val businessPlace get() :Flow<BusinessPlace> = _businessPlace
    /** Document Text Change */
    val isReportChanged = MutableStateFlow(false)
   private val _quickSentence = MutableStateFlow("")
    val quickSentence get(): StateFlow<String> = _quickSentence

    var contentText :String = ""
    var contentTextPos : Int = 0


    fun refreshDocument(docNo :Int){
        viewModelScope.launch{
            if (!isReportChanged.value)
                _document.emit(appRepository.getDocument(docNo))
                Log.d("ProgressReportFragment","refreshDocument!!")
            }
    }
    fun refreshPatient(patNo :Int){
        viewModelScope.launch{ _patient.emit(appRepository.getPatient(patNo)) }
    }
    fun refreshPlace(bpNo :Int){
        viewModelScope.launch{ _businessPlace.emit(appRepository.getBusinessPlace(bpNo)) }
    }
    fun updateDocument(contents:String){
        viewModelScope.launch {
            if(document.value.isValid()) {
                document.value.let {
                    val doc = Document(it.docNo,it.patNo,it.tmpNo,it.crtDate,contents)
                    appRepository.setDocument(doc)
                }
            }
        }
    }

    fun refreshSentence(sentence: Array<String>) {
        viewModelScope.launch{
            _quickSentence.value = arrayToLines(sentence).let { lines->
                if (contentText == "") lines else "\n$lines"
            }
        }
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