package com.nuhlp.nursehelper.learningtest


import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.collections.List




@JsonClass(generateAdapter = true)
class Person  (
    val name :String ,
    val age:Int )
{
    val type = 1
    fun adapter(moshi: Moshi):JsonAdapter<Person>{

       return  moshi.adapter(Person::class.java)
    }

}
data class Box(
    val size :Int ,
    val count:Int ){
    val type = 2
}




class moshi(){
    lateinit var moshi:Moshi
    lateinit var  adapter : JsonAdapter<Person>
    lateinit var list : List<Person>
    @SuppressLint("CheckResult")
    @Before
    fun initData(){
        moshi=  Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        adapter = moshi.adapter(Person::class.java)
        list = listOf(
            Person("sangmun",12),
            Person("hojin",14),
            Person("ujung",12)
        )
        val a = Person::class.java
    }

    @Test
    fun dataClassToJson(){
        val json = """ {"name":"${list[0].name}","age":${list[0].age}} """.trim()
        val dataClass: String = adapter.toJson(list[0])
        assertEquals(dataClass,json)
    }

    @Test
    fun jsonToDataClass(){
        val json = """ {"name":"${list[0].name}","age":${list[0].age}} """.trim()
       val dataClass = adapter.fromJson(json)!!
        assertEquals(dataClass.name,list[0].name)
        assertEquals(dataClass.age,list[0].age)
    }

    @Test
    fun dataClassTOJsonWithType(){

    }

    @Test
    fun getAdapterWithClassT(){
       val a  = TestC(list[0].name,list[0].age)
        a.jc.apply {
          this

        }

    }
}



abstract class jsonable {
    abstract val jc : Class<*>
    val moshi by lazy{ Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build() }
    fun <T> getAdapter(c : Class<T>): JsonAdapter<T> {
        return moshi.adapter(c)
    }

    fun fromJson(json:String) {
        getAdapter(jc).fromJson(json)
        // todo jc 에 설정 만으로 클랫스가 만들어지게 하기
    }
    fun getC(){
        println(jc.classes)
    }
    //abstract fun fromJson(json:String) : Any
}

class TestC(val name: String , val age :Int): jsonable(){
    override val jc
        get() = this.javaClass


    /*override fun fromJson(json: String) : TestC {
        val a = getAdapter(this.javaClass).fromJson(json)
        return a!!
    }*/
}
