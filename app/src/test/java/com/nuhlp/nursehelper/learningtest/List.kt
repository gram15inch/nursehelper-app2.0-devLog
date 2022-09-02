package com.nuhlp.nursehelper.learningtest
import kotlinx.coroutines.flow.Flow
import org.junit.Test
import org.junit.Assert.assertEquals

class List {


    @Test fun listTest(){
        val ml = mutableListOf<Flow<Boolean>>()
        assertEquals(1,1)
    }

    @Test
    fun test1(){
        var list = mutableListOf<Int>()
        repeat(50){
            list.add(it)}
        //println("00 $list)")
        val str = "0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49".split(", ").toTypedArray()
        println("00 ${str[0]}")
    }

    @Test
    fun toArray(){
        val el =  emptyList<StringTest>().toTypedArray()
        assertEquals(0,el.size)
        el.forEach {
            throw IllegalAccessException()
        }
       assertEquals(el.isEmpty(),true)
    }
    @Test(expected = StringIndexOutOfBoundsException::class)
    fun outIndexTest(){
        var str = ""
        str.substring(0,str.lastIndex)
    }

    @Test(expected = StringIndexOutOfBoundsException::class)
    fun outIndexWithLet(){
        var str = ""
        var strArray = arrayOf<String>()
        strArray.forEach {

        }.let { str.substring(0,str.lastIndex) }
    }


}