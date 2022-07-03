package com.nuhlp.nursehelper.domain

import android.media.session.MediaSession
import android.util.Log
import android.widget.MultiAutoCompleteTextView
import java.lang.StringBuilder
import java.util.*

enum class Task(private val taskNo:Int) {
    BasicNursing(0),
    CurativeNursing(1),
    Inspection(2),
    Dose(3),
    Injections(4),
    Education(5),
    Training(6),
    Counseling(7),
    Request(8);

    fun no()=taskNo

    companion object{
        private val TaskList = listOf(BasicNursing,CurativeNursing,Inspection,Dose,Injections,Education,Training,Counseling,Request)
        private fun toListCallBack(stringTasks:String):List<Task>
        {
            val tasks = mutableListOf<Task>()
            val token = StringTokenizer(stringTasks)

            while(token.hasMoreTokens()){
                tasks.add(TaskList.getOrElse(token.nextToken().toInt(),
                    defaultValue = {throw IllegalAccessError("$it")}))
            }
            return tasks.toList()
        }

        fun toString(tasks :List<Task>):String{
           var sb = StringBuilder()
            tasks.forEach{ task ->
                sb.append("${task.no()} ")
            }
            return sb.toString()
        }

        fun toList(stringTasks:String) :List<Task>{
          return try{
                toListCallBack(stringTasks)
            }catch (e:IllegalAccessError){
                Log.e("Task","unknown Task ${e.message}")
                e.printStackTrace()
                listOf()
            }
        }

    }
}

/*
    기본간호 : Basic nursing [1]
    치료적 간호 : curative nursing [2]
    검 사 : inspection [3]
    투약 : dose  [4]
    주사 : injections [5]
    교육 : Education [6]
    훈련 : Training [7]
    상담 : Counseling [8]
    의뢰 : request [9]
*/