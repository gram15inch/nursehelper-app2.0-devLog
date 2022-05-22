package com.nuhlp.nursehelper.data.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface LoginDataStore{
    val IS_LOGIN : Preferences.Key<Boolean>
    val preferenceFlow : Flow<Boolean>
    suspend fun saveIsLoginToPreferencesStore(isLogin: Boolean)
}