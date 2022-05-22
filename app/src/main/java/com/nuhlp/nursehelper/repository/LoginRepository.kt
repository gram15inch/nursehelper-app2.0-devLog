package com.nuhlp.nursehelper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LoginRepository (private val dataStore: LoginDataStore) {

    val isLogin: Flow<Boolean> = dataStore.preferenceFlow

    suspend fun setIsLoginToDataStore(value: Boolean){
        withContext(Dispatchers.IO) {
            dataStore.saveIsLoginToPreferencesStore(value)
        }
    }



}