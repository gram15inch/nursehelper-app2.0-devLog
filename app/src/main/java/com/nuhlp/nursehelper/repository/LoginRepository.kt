package com.nuhlp.nursehelper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.data.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository (private val dataStore: DataStore) {

    val isLogin: LiveData<Boolean> = dataStore.preferenceFlow.asLiveData()

    suspend fun setIsLoginToLiveData(value: Boolean){
        withContext(Dispatchers.IO) {
            dataStore.saveIsLoginToPreferencesStore(value)
        }
    }

}