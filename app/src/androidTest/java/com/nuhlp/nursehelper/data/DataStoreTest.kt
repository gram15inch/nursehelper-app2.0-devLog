package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    lateinit var dispatcher: TestDispatcher
    lateinit var dataStore: DataStore
    lateinit var appContext : Context

    @Before
    fun setUp(){
          dispatcher = StandardTestDispatcher()
          appContext = InstrumentationRegistry.getInstrumentation().targetContext
          dataStore = DataStoreImpl(appContext)
          val IS_LOGIN : Preferences.Key<Boolean>
    }

    @Test
    fun createTest(){
        assertEquals(false,dataStore.preferenceFlow.asLiveData().value)

    }


    fun preferenceFlowTest(){

    }


    fun saveIsLoginToPreferencesStoreTest(){

    }


}