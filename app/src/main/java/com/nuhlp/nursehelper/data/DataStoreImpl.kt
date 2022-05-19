package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val LOGIN_PREFERENCES_NAME = "login_preferences"
// dataStore 안에 Flow<Preferences> 생산자가 포함되어 있음
private val Context.DATA_STORE : DataStore<Preferences> by preferencesDataStore(
    name = LOGIN_PREFERENCES_NAME
)
interface DataStore{
    val IS_LOGIN : Preferences.Key<Boolean>
    val preferenceFlow : Flow<Boolean>
    suspend fun saveIsLoginToPreferencesStore(isLogin: Boolean)
}

class DataStoreImpl(private val context: Context) :com.nuhlp.nursehelper.data.DataStore{
    override val IS_LOGIN = booleanPreferencesKey("is_login")


    // datastore 가 Preferences 노출하지않고 Boolean 만 노출시키게함
    override val preferenceFlow: Flow<Boolean> = context.DATA_STORE.data
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
            preferences[IS_LOGIN] ?: false
        }

    override suspend fun saveIsLoginToPreferencesStore(isLogin: Boolean) {
        //  edit : datastore 의 데이터를 트랜잭션 방식으로 업데이트(생산)
        context.DATA_STORE.edit { preferences ->
            preferences[IS_LOGIN] = isLogin
        }
    }

}