package com.nuhlp.nursehelper.ui.home

import android.app.Application
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
    private val appRepository = AppRepository(getAppDatabase(application))
    private val dummyList = listOf(Document(0,0,0,"dummy0")
    ,Document(1,0,1,"dummy1"))
    var docLive = appRepository.docList.asLiveData()
    var muDocLive = MutableLiveData(dummyList)

    fun setDoc(doc:Document) = CoroutineScope(Dispatchers.IO).launch{  appRepository.deleteAll()
        appRepository.setDocument(doc)
    }

    fun btnClick(){
        setDoc(Document(0,0,0,"doc1"))
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
    val count = MutableLiveData(0)
    val countList = MutableLiveData(listOf(0,1,2,3))


}


