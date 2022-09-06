package com.nuhlp.nursehelper.domain

import android.content.res.Resources
import com.nuhlp.nursehelper.R
import com.squareup.moshi.JsonClass

/*
- 가정간호 동의서 (인적사항,보호자 서명)
- 개인정보 수집 및 이용 동의서(보호자 서명)
- 가정간호 의뢰지 (텍스트, 체크박스)
- 가정간호 대상자 선정/부류 기준지 (체크박스)
- 가정간호 종결 요약지 (여러줄텍스트, 텍스트, 체크박스)
- 가정간호 경과 기록지 (여러줄텍스트)

- CONSENT TO HOME CARE (HUMAN MATTERS, GUARDIAN'S SIGNATURE)
- CONSENT TO COLLECT AND USE PERSONAL INFORMATION (SIGNATURE OF GUARDIAN)
- HOME CARE REQUEST FORM (TEXT, CHECK BOX)
- HOME NURSING PATIENT SELECTION / CLASSIFICATION FORM (CHECK BOX)
- HOME CARE TERMINATION SUMMARY FORM (MULTI-LINE TEXT, TEXT, CHECK BOX)
- HOME NURSING PROGRESS REPORT (MULTIPLE LINES OF TEXT)
*/


interface Template {
    enum class Type(private val tpNo: Int) {
        CONSENT_TO_HOME_CARE(0),
        CONSENT_TO_COLLECT_AND_USE_PERSONAL_INFORMATION(1),
        HOME_CARE_REQUEST_FORM(2),
        HOME_NURSING_PATIENT_SELECTION(3),
        HOME_CARE_TERMINATION_SUMMARY_FORM(4),
        HOME_NURSING_PROGRESS_REPORT(5);
        fun no() = tpNo
    }
}
@JsonClass(generateAdapter = true)
data class DATA_HOME_NURSING_PROGRESS_REPORT(val progressContents : String) :Template

object DocTemplate{
    fun toType(no:Int){
        when(no){
            0->{Template.Type.CONSENT_TO_HOME_CARE}
            1->{Template.Type.CONSENT_TO_COLLECT_AND_USE_PERSONAL_INFORMATION}
            2->{Template.Type.HOME_CARE_REQUEST_FORM}
            3->{Template.Type.HOME_NURSING_PATIENT_SELECTION}
            4->{Template.Type.HOME_CARE_TERMINATION_SUMMARY_FORM}
            5->{Template.Type.HOME_NURSING_PROGRESS_REPORT}
        }
    }
    fun toName(no:Int):String{
       return when(no){
            0->{Resources.getSystem().getString(R.string.CONSENT_TO_HOME_CARE)}
            1->{Resources.getSystem().getString(R.string.CONSENT_TO_COLLECT_AND_USE_PERSONAL_INFORMATION)}
            2->{Resources.getSystem().getString(R.string.HOME_CARE_REQUEST_FORM)}
            3->{Resources.getSystem().getString(R.string.HOME_NURSING_PATIENT_SELECTION)}
            4->{Resources.getSystem().getString(R.string.HOME_CARE_TERMINATION_SUMMARY_FORM)}
            5->{Resources.getSystem().getString(R.string.HOME_NURSING_PROGRESS_REPORT)}
           else->{""}
        }
    }
}


