package com.nuhlp.nursehelper.domain.parser

import com.nuhlp.nursehelper.domain.DATA_HOME_NURSING_PROGRESS_REPORT
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.IllegalArgumentException


object TemplateParser {
    private val moshi by lazy{  Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build() }
   private val _DATA_HOME_NURSING_PROGRESS_REPORT
         by lazy{ moshi.adapter(DATA_HOME_NURSING_PROGRESS_REPORT::class.java)}

    private fun<T> getAdapter(ct:Class<T>): JsonAdapter<*> {
        return when(ct){
            DATA_HOME_NURSING_PROGRESS_REPORT::class.java -> _DATA_HOME_NURSING_PROGRESS_REPORT
            else -> { println("e: $ct");throw  IllegalArgumentException()
            }
        }
    }

    fun<T> fromJson(json:String,ct:Class<T>):T{
        return getAdapter(ct).fromJson(json) as T
    }
    fun<T> toJson(dataClass:T):String{
        return (getAdapter(dataClass!!::class.java) as JsonAdapter<T>).toJson(dataClass)
    }

}