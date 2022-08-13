package com.nuhlp.nursehelper.learningtest

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Assert
import org.junit.Test

class FlowTest {

    @Test
    fun flowTest(){
        var count = 0
        fun flow1() = flow(){
            repeat(3){
                delay(1000)
                emit(count)
            }
        }

        runBlocking {
            flow1().collect(){
                Assert.assertEquals(count++,it)
            }
        }
    }

    @Test
    fun flowInFlow(){

        val flow1 = MutableSharedFlow<Int>()
        val flow2 =  MutableSharedFlow<Int>()
        val flow3 =  MutableSharedFlow<Int>()

        CoroutineScope(Dispatchers.IO).launch {
            flow1.collect(){
                println("flow1 $it")
                flow2.emit(it+1)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            flow2.collect(){
                println("flow2 $it")
                flow3.emit(it+1)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            flow3.collect(){
                println("flow3 $it")
                this.cancel()
            }
        }
        runBlocking {
            flow1.emit(1)
            println("end")
        }

    }



}