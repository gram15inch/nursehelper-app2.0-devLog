package com.nuhlp.nursehelper.learningtest


import kotlinx.coroutines.*
import org.junit.*
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import kotlin.concurrent.thread


@RunWith(MockitoJUnitRunner::class)
class Mockito {

    @Mock
    lateinit var people : People

    lateinit var testDispatcher: TestDispatcher
    @Before
    fun setUp(){
        testDispatcher = StandardTestDispatcher()
    }


    // ! [verify]
    @Test // 호출횟수 테스트
    fun verifyTimesTest(){
        people.showPeoPle()
        people.showPeoPle()
        verify(people, times(2)).showPeoPle()
        verify(people, never()).hidePeoPle()
    }
    @Test // 파라미터 테스트
    fun verifyWithParameterTest(){

        people.withPara(true)
        verify(people,times(1)).withPara(true)

        people.withPara(false)
        verify(people,times(1)).withPara(false)

        people.withPara(true)
        verify(people,times(3)).withPara(anyBoolean())

    }
    @Test // 위임호출 테스트
    fun verifyProxyTest(){

        val proxyPeople = ProxyPeople(people,testDispatcher)
        proxyPeople.showPeoPle()
        verify(people, times(1)).showPeoPle()

        proxyPeople.callWithPara(true)
        verify(people, times(1)).withPara(true)
        proxyPeople.callWithPara(false)
        verify(people, times(1)).withPara(false)

    }
    @Test // 코루틴내부 위임호출 테스트
    fun verifyProxyInCoroutineTest(){
        runTest(testDispatcher){
            println("test start")
            val proxyPeople = ProxyPeople(people,testDispatcher)

            proxyPeople.callWithParaInCoroutine(true)
            advanceUntilIdle()
            verify(people, times(1)).suspendWithPara(true)

        }

    }



   /* [헬퍼 클래스,메소드]*/

       interface People{
           fun showPeoPle()
           fun hidePeoPle()
           fun withPara(bool:Boolean)
           suspend fun suspendWithPara(bool:Boolean)
       }
       class ProxyPeople(private val people: People, private val dispatcher: TestDispatcher){

           fun showPeoPle() {
               people.showPeoPle()
           }

           fun callWithPara(bool: Boolean) {
               people.withPara(bool)
           }

           fun callWithParaInCoroutine(bool: Boolean){
               CoroutineScope(dispatcher).launch {
                   people.suspendWithPara(bool)
                   println("call callWithParaInCoroutine")
               }

           }
       }




    /*[공식문서 테스트]*/
    suspend fun fetchData(): String {
        delay(1000L)
        return "Hello world"
    }

    /* [Coroutine] runTest

     1. runTest 는 TestScope 를 만듭니다. 이는 항상 TestDispatcher 를 사용하는 CoroutineScope 의 구현입니다.
     2. TestScope 의 디스패처에서 사용하는 스케줄러의 대기열에 추가되는 코루틴을 추적하고
         이 스케줄러에 대기 중인 작업이 있는 한 반환하지 않습니다.
     3. TestDispatcher 구현에는 두 가지가 있습니다. StandardTestDispatcher, UnconfinedTestDispatcher
         둘 다 TestCoroutineScheduler 를 사용하여 가상 시간을 제어하고 테스트 내에서 실행 중인 코루틴을 관리합니다.
     4. 테스트에서 사용하는 스케줄러 인스턴스는 하나만 있어야 하며 모든 TestDispatchers 간에 공유되어야 합니다.
     */



    @Test
    fun dataShouldBeHelloWorld() = runTest {
        val data = fetchData() // 지연을 무시함, 하나의 스레드만 이용
        assertEquals("Hello world", data)
    }




    /*  [앱 테스트] */
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