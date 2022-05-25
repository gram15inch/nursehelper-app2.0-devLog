package com.nuhlp.nursehelper.learningtest

import org.junit.Test
import org.junit.Assert.assertEquals


class Enum {


    @Test
   fun createTest(){
       assertEquals(1, testState.STATE1.CODE())
       assertEquals(2, testState.STATE2.CODE())
       assertEquals(2, testState.STATE2.CODE())

   }

enum class testState(private val CODE :Int){
    STATE1(1),
    STATE2(2);
    fun CODE () = CODE
}

}
