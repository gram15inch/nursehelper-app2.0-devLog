package com.nuhlp.nursehelper

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.MutableLiveData
import com.nuhlp.nursehelper.data.DataStore
import com.nuhlp.nursehelper.data.DataStoreImpl
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.InjectMocks

import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class LearningTest {

    @Mock
    lateinit var people : People

    @Test
    fun verifyTest(){
        people.showPeoPle()

        verify(people, times(1)).showPeoPle()
        verify(people, never()).hidePeoPle()

    }

    interface People{
        fun showPeoPle()
        fun hidePeoPle()
    }
}