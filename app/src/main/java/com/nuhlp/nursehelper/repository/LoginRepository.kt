package com.nuhlp.nursehelper.repository

import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import com.nuhlp.nursehelper.data.room.UserAccount
import com.nuhlp.nursehelper.data.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LoginRepository(private val dataStore: LoginDataStore,private val room: UserDatabase) {

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
    suspend fun setUserToDatabase(user: UserAccount){
        withContext(Dispatchers.IO) {
            room.userDao.setUser(user)
        }
    }

    fun isUserId(userId: String):Boolean{
      return  when(room.userDao.checkId(userId)){
            1 -> true
            else -> false
        }
    }




}