package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    lateinit var dispatcher: TestDispatcher
    lateinit var dataStore: DataStore
    lateinit var context : Context
    lateinit var isLoginFlow : Flow<Boolean>
    @Before
    fun setUp(){
          dispatcher = StandardTestDispatcher()
          context = InstrumentationRegistry.getInstrumentation().targetContext
          dataStore = DataStoreImpl(context)
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

    @Test
    fun createWithAppContext(){
       dataStore= DataStoreImpl(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
        setPreferences(set = true, expected = true, flow = isLoginFlow)
        setPreferences(set = false, expected = false, flow = isLoginFlow)
    }

}