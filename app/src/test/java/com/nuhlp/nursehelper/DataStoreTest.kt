package com.nuhlp.nursehelper



import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import com.nuhlp.nursehelper.data.DataStore
import com.nuhlp.nursehelper.data.DataStoreImpl
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DataStoreTest {
    lateinit var testDispatcher: TestDispatcher
    lateinit var dataStore : DataStore
    lateinit var applicationContext : Context

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    // todo 액티비티 꺼내기 찾기부터 시작


    @Before
    fun setUp(){
        testDispatcher = StandardTestDispatcher()
        applicationContext = ApplicationProvider.getApplicationContext()
        dataStore = DataStoreImpl(applicationContext)
    }

    @Test
    fun preferenceFlow(){

        assertEquals(dataStore.preferenceFlow.asLiveData().value,false)
    }

    @Test
    fun saveIsLoginToPreferencesStore(){
        runTest {

            dataStore.preferenceFlow
        }
    }
}