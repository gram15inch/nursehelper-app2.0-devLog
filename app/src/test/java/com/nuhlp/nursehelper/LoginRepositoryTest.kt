package com.nuhlp.nursehelper




import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import com.nuhlp.nursehelper.data.room.user.UserAccount
import com.nuhlp.nursehelper.data.room.user.UserDao
import com.nuhlp.nursehelper.data.room.user.UserDatabase
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
    @Mock
    lateinit var mockDatabase: UserDatabase

    @Mock
    lateinit var mockDao: UserDao

    lateinit var loginRepository: LoginRepository
    lateinit var testDispatcher: TestDispatcher

    lateinit var users : List<UserAccount>

    @Before
    fun setUp(){
                testDispatcher = StandardTestDispatcher()
                users = listOf(
                UserAccount( "user1", "pw1", "20220101"),
            UserAccount( "user2", "pw2", "20220102"),
            UserAccount( "user3", "pw3", "20220103"),
            UserAccount( "user4", "pw4", "20220104"),
            UserAccount( "user5", "pw5", "20220104")
                )

        `when`(mockDataStore.getPreferenceFlow(DataStoreKey.IS_LOGIN)).thenReturn(flow { emit(true) }) // 의미없는 flow
        `when`(mockDataStore.getPreferenceFlow(DataStoreKey.IS_AGREE_TERMS)).thenReturn(flow { emit(true) })
        `when`(mockDatabase.userDao).thenReturn(mockDao)
        loginRepository = LoginRepository(mockDataStore,mockDatabase)
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

    @Test // id 일치갯수 반환후 bool 변환 테스트
    fun getAvailableIdAsBoolTest(){
        `when`(mockDao.getAvailableId(users[0].id)).thenReturn(flow { emit(1) })
        `when`(mockDao.getAvailableId(users[1].id)).thenReturn(flow { emit(0) })

        verify(mockDatabase.userDao, times(0)).getAvailableId(users[0].id)

        runTest(testDispatcher){
            assertEquals(loginRepository.getAvailableId(users[0].id).first(),true)
            assertEquals(loginRepository.getAvailableId(users[1].id).first(),false)
        }

        verify(mockDatabase.userDao, times(1)).getAvailableId(users[0].id)


    }

    @Test // user 일치갯수 반환후 bool 변환 테스트
    fun validUsersAsBoolTest(){
        `when`(mockDao.countExistedUser(users[0].id,users[0].pw)).thenReturn( 1)
        `when`(mockDao.countExistedUser(users[1].id,users[1].pw)).thenReturn( 0)


        verify(mockDatabase.userDao, times(0)).countExistedUser(users[0].id,users[0].pw)

        runTest(testDispatcher){ // asBool 로 받아옴
            assertEquals(loginRepository.validUser(users[0]),true)
            assertEquals(loginRepository.validUser(users[1]),false)
        }

        verify(mockDatabase.userDao, times(1)).countExistedUser(users[0].id,users[0].pw)


    }




}
