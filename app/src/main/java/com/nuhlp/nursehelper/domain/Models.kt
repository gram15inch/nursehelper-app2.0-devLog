package com.nuhlp.nursehelper.domain

import com.nuhlp.nursehelper.data.room.UserAccount

data class UserAccountModel(
    val accountNo: Int,
    val id: String,
    val pw: String,
) {

    /**
     * Short description is used for displaying truncated descriptions in the UI
     */
    /* val shortDescription: String
        get() = description.smartTruncate(200) */
}

/*
fun UserAccountModel.asRoomEntity():UserAccount{
    return UserAccount(
        accountNo = 0,
        id = id,
        pw= pw
    )
}*/
