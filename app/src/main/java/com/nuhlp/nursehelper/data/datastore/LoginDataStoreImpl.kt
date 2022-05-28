package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.lang.IllegalArgumentException

private const val LOGIN_PREFERENCES_NAME = "login_preferences"
// dataStore 안에 Flow<Preferences> 생산자가 포함되어 있음
private val Context.DATA_STORE : DataStore<Preferences> by preferencesDataStore(
    name = LOGIN_PREFERENCES_NAME
)


class LoginDataStoreImpl(private val context: Context) : LoginDataStore {

    private val _isLoginPreferenceFlow: Flow<Boolean> = preferenceFlowFactory(DataStoreKey.IS_LOGIN)
    private val _isAgreeTermsPreferenceFlow: Flow<Boolean> = preferenceFlowFactory(DataStoreKey.IS_AGREE_TERMS)


    override fun getPreferenceFlow(enum: DataStoreKey):Flow<Boolean> = when(enum){
        DataStoreKey.IS_LOGIN->{ _isLoginPreferenceFlow }
        DataStoreKey.IS_AGREE_TERMS->{ _isAgreeTermsPreferenceFlow }
    }

    // datastore 가 Preferences 노출하지않고 Boolean 만 노출시키게함
    private fun preferenceFlowFactory(enum: DataStoreKey): Flow<Boolean>{
        return context.DATA_STORE.data
            // 생산자 단계의 에러를 잡음 (파일에서의 읽고 쓰기중의 오류)
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences()) // 에러시 생산자 단계의 flow 를 비움
                } else {
                    throw it // 입출력 이외의 오류시 예외 다시발생
                }
            } // 중간 연산자
            .map { preferences ->
                // 처음에는 값이 비어있으므로 기본 false 반환
                preferences[enum.KEY] ?: false
            }
    }

    override suspend fun saveToPreferencesStore(state: Boolean,enum: DataStoreKey) {
        //  edit : datastore 의 데이터를 트랜잭션 방식으로 업데이트(생산)
        context.DATA_STORE.edit { preferences ->
            preferences[enum.KEY] = state
        }
    }



}