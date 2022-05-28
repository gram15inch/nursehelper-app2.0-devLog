package com.nuhlp.nursehelper




import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.flow.flow
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
    lateinit var mockDataStore: LoginDataStore

    lateinit var loginRepository: LoginRepository
    lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp(){
        testDispatcher = StandardTestDispatcher()
        `when`(mockDataStore.getPreferenceFlow(DataStoreKey.IS_LOGIN)).thenReturn(flow { emit(true) }) // 의미없는 flow
        `when`(mockDataStore.getPreferenceFlow(DataStoreKey.IS_AGREE_TERMS)).thenReturn(flow { emit(true) })

        loginRepository = LoginRepository(mockDataStore, )
    }


    @Test // 주입된 dataStore 호출 확인
    fun checkCallDataStore() {

        val choiceBool :(Boolean)->Unit= {bool->
            runTest {
                // isLogin check
                verify(mockDataStore, times(0)).saveToPreferencesStore(bool,DataStoreKey.IS_LOGIN)
                loginRepository.setIsLoginToDataStore(bool)
                verify(mockDataStore, times(1)).saveToPreferencesStore(bool,DataStoreKey.IS_LOGIN)

                // isAgreeTerms check
                verify(mockDataStore, times(0)).saveToPreferencesStore(bool,DataStoreKey.IS_AGREE_TERMS)
                loginRepository.setTermsToDataStore(bool)
                verify(mockDataStore, times(1)).saveToPreferencesStore(bool,DataStoreKey.IS_AGREE_TERMS)
            }
        }
        choiceBool(true)
        choiceBool(false)
    }





}
