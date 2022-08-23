package com.nuhlp.nursehelper.utill.useapp

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

object AppTime {
    val SDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    fun calculateAge(birthDate: LocalDate?, currentDate: LocalDate?): Int {
        return if (birthDate != null && currentDate != null) {
            Period.between(birthDate, currentDate).years
        } else {
            0
        }
    }
}
