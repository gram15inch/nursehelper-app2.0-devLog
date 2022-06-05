package com.nuhlp.nursehelper.learningtest

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.data.datastore.LoginDataStore
import com.nuhlp.nursehelper.ui.login.LoginActivity
import com.nuhlp.nursehelper.ui.login.LoginFragment
import com.nuhlp.nursehelper.ui.login.LoginViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class viewModel {

    lateinit var dispatcher: TestDispatcher
    lateinit var dataStore: LoginDataStore
    lateinit var context : Context
    lateinit var application : Application
    lateinit var loginViewModel: LoginViewModel
    lateinit var scenario : ActivityScenario<LoginActivity>
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp(){
        scenario = activityScenarioRule.scenario
        dispatcher = StandardTestDispatcher()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        scenario.onActivity { application = it.application
            loginViewModel = ViewModelProvider(it, LoginViewModel.Factory(application) )
                .get(LoginViewModel::class.java)
        }

    }


    @Test
    fun checkViewModelSingleton(){
        scenario.onActivity {
           val fragment = it.navHostFragment.childFragmentManager.fragments[0]
            if(fragment is LoginFragment )
                assertEquals(fragment._loginViewModel,loginViewModel )
            else
                throw IllegalArgumentException("fragment is not LoginFragment")
        }

    }


}