package com.nuhlp.nursehelper.utill.useapp
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

object AppTime {
    @SuppressLint("SimpleDateFormat")
    val SDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    @SuppressLint("SimpleDateFormat")
    val RRN = SimpleDateFormat("yyyyMMdd")
    @SuppressLint("SimpleDateFormat")
    val RRNF = SimpleDateFormat("yyyy년 M월 d일")
    fun calculateAge(birthDate: LocalDate?, currentDate: LocalDate?): Int {
        return if (birthDate != null && currentDate != null) {
            Period.between(birthDate, currentDate).years
        } else {
            0
        }
    }
    fun getAge(birthDate: Date?): Int {
        return if(birthDate!=null){
            val today =Calendar.getInstance().get(Calendar.YEAR)
            val birthCalendar = Calendar.getInstance()
            birthCalendar.time = birthDate
            val birth = birthCalendar.get(Calendar.YEAR)
            today - birth +1
        }else 0
    }
}
