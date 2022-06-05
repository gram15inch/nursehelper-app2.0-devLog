package com.nuhlp.nursehelper.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nuhlp.nursehelper.ui.login.LoginActivity
import com.nuhlp.nursehelper.ui.login.LoginViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {
    lateinit var dispatcher: TestDispatcher
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
}