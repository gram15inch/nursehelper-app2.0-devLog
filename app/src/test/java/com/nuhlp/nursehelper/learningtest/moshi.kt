package com.nuhlp.nursehelper.learningtest


import android.annotation.SuppressLint
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException
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

    @Test // 전역 adapter 사용
    fun classToJsonWithGlobal(){
        val json = """ {"name":"${list[0].name}","age":${list[0].age}} """.trim()
        val dataClass: String = adapter.toJson(list[0])
        assertEquals(dataClass,json)
    }

    @Test // 전역 adapter 사용
    fun jsonToClassWithGlobal(){
        val json = """ {"name":"${list[0].name}","age":${list[0].age}} """.trim()
       val dataClass = adapter.fromJson(json)!!
        assertEquals(dataClass.name,list[0].name)
        assertEquals(dataClass.age,list[0].age)
    }


    @Test // json -> data class (Factory 멤버변수 사용)
    fun jsonToClassWithProperty(){
        val json = """ {"name":"${list[0].name}","age":${list[0].age}} """.trim()
        val dataClass = JsonFactory.fromJson(json, TypeTest1::class.java)
        assertEquals(dataClass.name,list[0].name)
        assertEquals(dataClass.age,list[0].age)
    }

    @Test // data class -> json (Factory 멤버변수 사용)
    fun classToJsonWithProperty(){
        val json = """ {"name":"${list[0].name}","age":${list[0].age}} """.trim()
        val dataClassToJson = JsonFactory.toJson(list[0])
        assertEquals(json,dataClassToJson)
    }
}


object JsonFactory{
    val moshi by lazy{  Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build() }
    val test1A by lazy{ moshi.adapter(TypeTest1::class.java)}
    val test2A by lazy{ moshi.adapter(TypeTest2::class.java)}
    val personA by lazy{ moshi.adapter(Person::class.java)}

    private fun<T> getAdapter(ct:Class<T>):JsonAdapter<*>{
        return when(ct){
            TypeTest1::class.java -> test1A
            TypeTest2::class.java -> test2A
            Person::class.java -> personA
            else -> { println("e: $ct");throw  IllegalArgumentException()}
        }
    }

    fun<T> fromJson(json:String,ct:Class<T>):T{
       return getAdapter(ct).fromJson(json) as T
    }
    fun<T> toJson(dataClass:T):String{
        return (getAdapter(dataClass!!::class.java) as JsonAdapter<T>).toJson(dataClass)
    }

    // ** test 용 **
     private  fun<T> adapter(ct:Class<T>):JsonAdapter<T>{
        return moshi.adapter(ct)
    }

}

abstract class typeable {
    abstract val adapter :JsonAdapter<*>
    //abstract fun toJson():String

    // todo json 에 있는 데이터를 외부 참조없이 data class 로 변경하기
    // json -> datClass (adapter.formJson)
    // dataClass -> json  (adapter.toJson)
    // 어답터 채택기 ( json 혹은 dataclass 의 타입 속성을 확인하여 어답터 채택) - 채택후 반환타입을 변경할 수 없어 안됨
    // 어답터 생성기

}

class TypeTest1(val name: String , val age :Int){

}
class TypeTest2(val name: String , val age :Int){}