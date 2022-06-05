package com.nuhlp.nursehelper.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.ui.main.MainActivity
import com.nuhlp.nursehelper.ui.main.MainViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    lateinit var dispatcher: TestDispatcher
    lateinit var context : Context
    lateinit var application : Application
    lateinit var mainViewModel: MainViewModel
    lateinit var mainActivity : MainActivity
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test fun testEvent() {
        assertEquals("created" ,(mainViewModel).checkCreate())
    }
    @Before
    fun setUp(){
        val scenario = activityScenarioRule.scenario
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        scenario.onActivity { application = it.application
            mainViewModel = ViewModelProvider(it, MainViewModel.Factory(application) )
                .get(MainViewModel::class.java)
        }
    }

    @Test
    fun createWithFactory(){
        assertEquals(1,1)
    }

}