package com.nuhlp.nursehelper

import android.app.Application
import androidx.lifecycle.*

class MainViewModel (application: Application) : AndroidViewModel(application) {

/* todo 되돌리기

    private val loginRepository = LoginRepository(DataStore(application))

    val isLogin : LiveData<Boolean> = loginRepository.isLogin

    fun loginSuccess(){
        viewModelScope.launch{
            loginRepository.setIsLogin(true)
        }
    }

    fun loginFail(){
        viewModelScope.launch{
            loginRepository.setIsLogin(false)
        }
    }

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }
    private fun refreshDataFromRepository() {
      */
/*  viewModelScope.launch {
            try {
                videosRepository.refreshVideos()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }*//*

    }

    */
/**
     * Resets the network error flag.
     *//*

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
       override fun <T : ViewModel> create(modelClass: Class<T>): T {
           if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
               @Suppress("UNCHECKED_CAST")
               return MainViewModel(app) as T
           }
           throw IllegalArgumentException("Unable to construct viewmodel")
       }
    }
*/

}