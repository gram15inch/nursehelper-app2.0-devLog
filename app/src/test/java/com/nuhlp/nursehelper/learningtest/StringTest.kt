package com.nuhlp.nursehelper.learningtest
import kotlinx.coroutines.flow.Flow
import org.junit.Test
import org.junit.Assert.assertEquals

class StringTest {



    @Test
    fun subString1(){
        var str = ""
        repeat(2){
            str += "$it "
        }
        assertEquals(3, str.lastIndex)
        str= str.substring(0,str.lastIndex)
        assertEquals(str, "0 1")
    }
    @Test
    fun subString2(){
        var str = "1\n"
        assertEquals(2, str.length)
        assertEquals(1, str.lastIndex)

        str += "2\n"
        str = str.substring(0,str.lastIndex)
        assertEquals(str, "1\n2")
    }

}