package com.nuhlp.nursehelper.data.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

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

}


