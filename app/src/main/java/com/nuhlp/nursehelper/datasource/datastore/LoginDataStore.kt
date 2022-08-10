package com.nuhlp.nursehelper.datasource.datastore

import kotlinx.coroutines.flow.Flow

interface LoginDataStore{
    suspend fun saveToPreferencesStore(state: Boolean, enum: DataStoreKey)
    fun getPreferenceFlow(enum: DataStoreKey):Flow<Boolean>
}