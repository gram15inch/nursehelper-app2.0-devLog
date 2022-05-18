package com.nuhlp.nursehelper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.data.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository (private val dataStoreImpl: DataStore) {

    val isLogin: LiveData<Boolean> = dataStoreImpl.preferenceFlow.asLiveData()

    suspend fun setIsLoginToLiveData(value: Boolean){
        withContext(Dispatchers.IO) {
            dataStoreImpl.saveIsLoginToPreferencesStore(value)
        }
    }

}