package com.nuhlp.nursehelper.learningtest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class Coroutine {
    lateinit var dispatcher: TestDispatcher

    @Mock
    lateinit var people : People

    @Before
    fun setUp(){
        dispatcher = StandardTestDispatcher()
    }

    @Test // 코루틴내부 위임호출 테스트
    fun verifyProxyInCoroutineTest(){
        runTest(dispatcher){
            println("test start")
            val proxyPeople = ProxyPeople(people,dispatcher)

            proxyPeople.callWithParaInCoroutine(true)
            advanceUntilIdle() // 앞의 코루틴 대기
            Mockito.verify(people, Mockito.times(1)).suspendWithPara(true)

        }

    }



    /* todo [헬퍼 클래스,메소드]*/
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
}