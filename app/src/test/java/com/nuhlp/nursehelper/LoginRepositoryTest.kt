package com.nuhlp.nursehelper


import android.content.Context
import android.test.mock.MockContext
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.nuhlp.nursehelper.data.DataStore
import com.nuhlp.nursehelper.data.DataStoreImpl
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith


import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {
    lateinit var mockDataStoreImpl: DataStoreImpl
    lateinit var loginRepository : LoginRepository

    @get:Rule
    var initRule: MockitoRule = MockitoJUnit.rule()

    @Mock lateinit var dataStore :DataStore
    @Before
    fun setUp(){
        `when`(dataStore.IS_LOGIN)
            .thenReturn(booleanPreferencesKey("is_login"))
        `when`(dataStore.preferenceFlow)
            .thenReturn(MutableFlow)
        runBlocking {`when`(dataStore.saveIsLoginToPreferencesStore(true))
            .then {  }}
        loginRepository = LoginRepository(dataStore)

    }

    @Test
    fun setIsLoginToLiveData() {
        runBlocking {
            loginRepository.setIsLoginToLiveData(true)
            verify(mockDataStoreImpl, times(1)).saveIsLoginToPreferencesStore(true)

        }
        Assert.assertEquals(10,10)
    }


}
