package com.nuhlp.nursehelper.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow

interface LoginDataStore{
    suspend fun saveToPreferencesStore(state: Boolean, enum: DataStoreKey)
    fun getPreferenceFlow(enum: DataStoreKey):Flow<Boolean>
}