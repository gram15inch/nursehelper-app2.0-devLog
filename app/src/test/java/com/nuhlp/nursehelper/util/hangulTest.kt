package com.nuhlp.nursehelper.util

import com.nuhlp.nursehelper.utill.useapp.Hangul
import org.junit.Assert.assertEquals
import org.junit.Test

class hangulTest {


    @Test
    fun getFirst(){
        var f= Hangul.getInitialSound("피자")
        assertEquals("ㅍ",f)

        f= Hangul.getInitialSound("pizza")
        assertEquals(null,f)

        f= Hangul.getInitialSound("")
        assertEquals(null,f)

    }
}