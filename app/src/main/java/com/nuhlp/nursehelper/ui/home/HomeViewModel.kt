package com.nuhlp.nursehelper.ui.home

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {
    private val appRepository = AppRepository(getAppDatabase(application))
    val docLive = MutableLiveData<Document>()
    // todo 뻑나는거 고치기

    fun setDoc(doc:Document) =viewModelScope.launch{  appRepository.setDocument(doc)}

    fun getDoc(no:Int) =viewModelScope.launch{ docLive.value = appRepository.getDocument(no) }

    fun btnClick(){ setDoc(Document(0,0,0,"doc1"))
        getDoc(0) }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
    // ** test 용 **
    val count = MutableLiveData(0)


}


