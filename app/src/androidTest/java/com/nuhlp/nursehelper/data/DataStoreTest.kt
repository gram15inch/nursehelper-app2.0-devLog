package com.nuhlp.nursehelper.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.data.datastore.DataStoreKey
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
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


@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    lateinit var dispatcher: TestDispatcher
    lateinit var dataStore: LoginDataStore
    lateinit var context : Context
    lateinit var isLoginFlow : Flow<Boolean>
    lateinit var isAgreeTermsFlow : Flow<Boolean>

    @Before
    fun setUp(){
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        dataStore = LoginDataStoreImpl(context)
        isLoginFlow = dataStore.getPreferenceFlow(DataStoreKey.IS_LOGIN)
        isAgreeTermsFlow= dataStore.getPreferenceFlow(DataStoreKey.IS_AGREE_TERMS)
    }

    @Test // 기본
    fun isLoginDefaultFalse(){
        runTest {
            assertEquals(false,isLoginFlow.first())
            assertEquals(false,isAgreeTermsFlow.first())
        }
    }

    @Test
    fun checkSetPreferences(){
        setPreferences(set = true, expected = true, flow = isLoginFlow,enum = DataStoreKey.IS_LOGIN)
        setPreferences(set = false, expected = false, flow = isLoginFlow,enum = DataStoreKey.IS_LOGIN)
        setPreferences(set = true, expected = true, flow = isAgreeTermsFlow,enum = DataStoreKey.IS_AGREE_TERMS)
        setPreferences(set = false, expected = false, flow = isAgreeTermsFlow,enum = DataStoreKey.IS_AGREE_TERMS)
    }

    fun setPreferences(set: Boolean, expected:Boolean, flow: Flow<Boolean>,enum :DataStoreKey){
        runTest {
            dataStore.saveToPreferencesStore(set,enum)
            advanceUntilIdle()
            assertEquals(expected,flow.first())
        }
    }

    @Test
    fun createWithAppContext(){
       dataStore= LoginDataStoreImpl(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
        setPreferences(set = true, expected = true, flow = isLoginFlow,DataStoreKey.IS_LOGIN)
        setPreferences(set = false, expected = false, flow = isLoginFlow,DataStoreKey.IS_LOGIN)
    }

}