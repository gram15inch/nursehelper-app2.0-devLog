package com.nuhlp.nursehelper.data.room.user

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nuhlp.nursehelper.utill.useapp.AppTime
import java.util.*


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
       fun asFormattedDate(date: Calendar): String =  AppTime.SDF.format(date.time)

    }
}



