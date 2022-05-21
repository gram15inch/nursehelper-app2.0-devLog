package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    lateinit var dispatcher: TestDispatcher
    lateinit var dataStore: DataStore
    lateinit var appContext : Context
    lateinit var isLoginFlow : Flow<Boolean>
    @Before
    fun setUp(){
          dispatcher = StandardTestDispatcher()
          appContext = InstrumentationRegistry.getInstrumentation().targetContext
          dataStore = DataStoreImpl(appContext)
          isLoginFlow = dataStore.preferenceFlow
    }

    @Test
    fun isLoginDefaultFalse(){
        runTest {
            assertEquals(false,isLoginFlow.first())
        }
    }

    @Test
    fun checkSetPreferences(){
        setPreferences(set = true, expected = true, flow = isLoginFlow)
        setPreferences(set = false, expected = false, flow = isLoginFlow)
    }

    fun setPreferences(set: Boolean, expected:Boolean, flow: Flow<Boolean>){
        runTest {
            dataStore.saveIsLoginToPreferencesStore(set)
            advanceUntilIdle()
            assertEquals(expected,flow.first())
        }
    }


}