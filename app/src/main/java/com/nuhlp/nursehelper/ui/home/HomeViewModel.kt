package com.nuhlp.nursehelper.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.nuhlp.nursehelper.data.room.app.getAppDatabase
import com.nuhlp.nursehelper.repository.AppRepository

class HomeViewModel (application: Application) : AndroidViewModel(application) {
    private val appRepository = AppRepository(
        getAppDatabase(application)
    )

/*
    val isLogin : LiveData<Boolean> = loginRepository.isLogin.asLiveData()
    val isAgreeTerm : LiveData<Boolean> = loginRepository.isAgreeTerms.asLiveData()
*/




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