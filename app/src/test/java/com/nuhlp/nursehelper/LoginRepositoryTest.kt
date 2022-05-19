package com.nuhlp.nursehelper



import androidx.datastore.preferences.core.booleanPreferencesKey
import com.nuhlp.nursehelper.data.DataStore
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*


import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {


    @Mock
    lateinit var mockDataStore: DataStore

    lateinit var loginRepository: LoginRepository
    lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp(){
        testDispatcher = StandardTestDispatcher()
        `when`(mockDataStore.preferenceFlow).thenReturn(flow { emit(true) })
        `when`(mockDataStore.IS_LOGIN).thenReturn(booleanPreferencesKey("is_login"))
        loginRepository = LoginRepository(mockDataStore)
    }


    @Test
    fun setIsLoginToLiveData() {

        val choiceBool :(Boolean)->Unit= {bool->
            runTest {
                loginRepository.setIsLoginToLiveData(bool)
                verify(mockDataStore, times(1)).saveIsLoginToPreferencesStore(bool)
            }
        }
        choiceBool(true)
        choiceBool(false)
    }





}
