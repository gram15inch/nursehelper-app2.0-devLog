package com.nuhlp.nursehelper.data.room

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * DatabaseVideo represents a video entity in the database.
 */
@Entity
data class UserAccount constructor(
    @PrimaryKey
    val id: String,
    val pw: String,
    val registrationDate: String,
) {

    fun isValid():Boolean = (id !="" && pw != "" && registrationDate !="")
    @SuppressLint("SimpleDateFormat")
    companion object{
       fun asFormattedDate(date: Calendar): String =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.time)
    }
}



