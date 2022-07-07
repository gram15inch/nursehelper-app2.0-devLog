package com.nuhlp.nursehelper.ui.home

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository

class HomeViewModel (application: Application) : AndroidViewModel(application) {
    private val appRepository = AppRepository(
        getAppDatabase(application)
    )
        val count = MutableLiveData(0)

fun countUp(){
    count.value = count.value!! + 1
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
}


