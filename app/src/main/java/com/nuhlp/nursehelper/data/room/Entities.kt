package com.nuhlp.nursehelper.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DatabaseVideo represents a video entity in the database.
 */
@Entity
data class UserAccount constructor(

    @PrimaryKey(autoGenerate = true)
    val accountNo: Long,
    val id: String,
    val pw: String,
    val registrationDate: String,


) {

}


