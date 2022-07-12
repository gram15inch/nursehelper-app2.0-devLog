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
    private val appRepository = AppRepository(getAppDatabase(application))
    private val dummy = Document(0,0,0,"dummy0")
    var docLive = appRepository.docList.asLiveData()
    var docLiveT = MutableLiveData<Document?>()
    init {
        CoroutineScope(Dispatchers.IO).launch{
            appRepository.deleteAll()
        }
    }
    fun setDoc(doc:Document) = CoroutineScope(Dispatchers.IO).launch{
        appRepository.setDocument(doc)
    }
    var countT = 0
    fun btnClick(){
        setDoc(Document(countT,0,0,"doc$countT"))
        countT++
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


