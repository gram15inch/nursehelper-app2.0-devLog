package com.nuhlp.nursehelper.datasource.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

enum class DataStoreKey(val KEY: Preferences.Key<Boolean>) {
    IS_LOGIN(booleanPreferencesKey("is_login")),
    IS_AGREE_TERMS(booleanPreferencesKey("is_agree_terms"));
}

