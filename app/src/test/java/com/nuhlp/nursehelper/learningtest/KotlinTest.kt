package com.nuhlp.nursehelper.learningtest

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import org.junit.Assert
import org.junit.Test

class KotlinTest {
    @Test
    fun test1(){
        repeat(1){
            assertEquals(0, it)
        }
    }

    @Test
    fun test2(){
        repeat(1){t1->
            assertEquals(1,1/(t1+1))
        }
    }

}