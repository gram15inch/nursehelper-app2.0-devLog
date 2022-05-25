package com.nuhlp.nursehelper.repository

import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LoginRepository (private val dataStore: LoginDataStore) {

    val isLogin: Flow<Boolean> = dataStore.getPreferenceFlow(DataStoreKey.IS_LOGIN)
    val isAgreeTerms: Flow<Boolean> = dataStore.getPreferenceFlow(DataStoreKey.IS_AGREE_TERMS)

    suspend fun setIsLoginToDataStore(value: Boolean){
        withContext(Dispatchers.IO) {
            dataStore.saveToPreferencesStore(value,DataStoreKey.IS_LOGIN)
        }
    }
    suspend fun setTermsToDataStore(value: Boolean){
        withContext(Dispatchers.IO) {
            dataStore.saveToPreferencesStore(value,DataStoreKey.IS_AGREE_TERMS)
        }
    }





}