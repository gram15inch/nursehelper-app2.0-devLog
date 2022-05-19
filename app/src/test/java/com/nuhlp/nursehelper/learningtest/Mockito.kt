package com.nuhlp.nursehelper.learningtest


import kotlinx.coroutines.Dispatchers
import org.mockito.Mock
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.*
import org.junit.runner.RunWith

import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class Mockito {

    @Mock
    lateinit var people : People

    @Test
    fun verifyTimesTest(){
        // 호출횟수 테스트
        people.showPeoPle()
        people.showPeoPle()
        verify(people, times(2)).showPeoPle()
        verify(people, never()).hidePeoPle()
    }
    @Test
    fun verifyWithParameterTest(){
        // 파라미터 테스트
        people.inPara(true)
        verify(people,times(1)).inPara(true)

        people.inPara(false)
        verify(people,times(1)).inPara(false)

        people.inPara(true)
        verify(people,times(3)).inPara(anyBoolean())

    }
    @Test
    fun verifyProxyTest(){
        // 위임호출 테스트
        val proxyPeople = ProxyPeople(people)
        proxyPeople.showPeoPle()
        verify(people, times(1)).showPeoPle()

        proxyPeople.callWithPara(true)
        verify(people, times(1)).inPara(true)
        proxyPeople.callWithPara(false)
        verify(people, times(1)).inPara(false)

    }
    @Test
    fun verifyProxyInCoroutineTest(){
        // 코루틴내부 위임호출 테스트
        val proxyPeople = ProxyPeople(people)
        runBlocking {
            proxyPeople.callWithParaInCoroutine(true)
            verify(people, times(1)).inPara(true)
        }

    }


    
   /*  헬퍼 클래스,메소드*/
    interface People{
        fun showPeoPle()
        fun hidePeoPle()
        fun inPara(bool:Boolean)
    }
    class ProxyPeople(private val people: People){
         fun showPeoPle() {
             people.showPeoPle()
        }

         fun hidePeoPle() {
           people.hidePeoPle()
        }

         fun callWithPara(bool: Boolean) {
           people.inPara(bool)
        }

       suspend fun callWithParaInCoroutine(bool: Boolean){
            withContext(Dispatchers.IO) {
                people.inPara(bool)
            }
        }
    }



    /* 앱 테스트*/
    @Mock
    lateinit var testMock : TestDataStore
    @Test
    fun testRepositoryTest(){
        val lr = TestLoginRepository(testMock)
        runBlocking {
            lr.setData(true)
            verify(testMock, times(1)).setData(true)
        }
    }


    interface TestDataStore {
        fun setData(boolean: Boolean)
    }
    class TestLoginRepository(private val testDataStore: TestDataStore) {

        suspend fun setData(boolean: Boolean){
            withContext(Dispatchers.IO){
                testDataStore.setData(boolean)
            }
        }
    }


}