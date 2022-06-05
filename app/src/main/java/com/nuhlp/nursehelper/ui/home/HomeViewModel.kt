package com.nuhlp.nursehelper.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nuhlp.nursehelper.ui.main.MainViewModel

class HomeViewModel (application: Application) : AndroidViewModel(application) {







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