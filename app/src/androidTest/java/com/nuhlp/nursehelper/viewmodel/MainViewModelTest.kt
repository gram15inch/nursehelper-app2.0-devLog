package com.nuhlp.nursehelper.viewmodel

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.data.DataStore
import com.nuhlp.nursehelper.data.DataStoreImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    lateinit var dispatcher: TestDispatcher
    lateinit var appContext : Context

    @Before
    fun setUp(){
        dispatcher = StandardTestDispatcher()
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun checkIsLogin(){

    }
}