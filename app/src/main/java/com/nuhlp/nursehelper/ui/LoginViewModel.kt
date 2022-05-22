package com.nuhlp.nursehelper.ui

import android.app.Application
import androidx.lifecycle.*
import com.nuhlp.nursehelper.MainViewModel
import com.nuhlp.nursehelper.data.DataStoreImpl
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application){
    private val loginRepository = LoginRepository(DataStoreImpl(application))
    val isLogin : LiveData<Boolean> = loginRepository.isLogin.asLiveData()

    fun loginSuccess(){
        viewModelScope.launch{
            loginRepository.setIsLoginToDataStore(true)
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}