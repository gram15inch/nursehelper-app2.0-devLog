package com.nuhlp.nursehelper.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {
    init { CoroutineScope(Dispatchers.IO).launch{  } } // todo 전체삭제

    private val appRepository = AppRepository(getAppDatabase(application))
    var docLive = appRepository.docList.asLiveData()

    fun setDoc(doc:Document) = CoroutineScope(Dispatchers.IO).launch{
        appRepository.setDocument(doc)
    }

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

    private var countT = 0
    fun btnClick(){
        setDoc(Document(countT,0,0,"20220101","doc$countT"))
        countT++
    }


}


