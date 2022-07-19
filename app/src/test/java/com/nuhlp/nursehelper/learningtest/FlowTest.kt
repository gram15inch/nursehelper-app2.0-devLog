package com.nuhlp.nursehelper.learningtest

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
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


}