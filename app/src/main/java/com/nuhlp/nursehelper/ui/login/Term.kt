package com.nuhlp.nursehelper.ui.login

enum class Term(val CODE : Int) {
    ESSENTIAL(1),
    USERINFO(2);

    fun toTermName() :String = when(this.CODE){
        1->{"ESSENTIAL"}
        2->{"USERINFO"}
        else -> {"unknown code"}
    }
}
