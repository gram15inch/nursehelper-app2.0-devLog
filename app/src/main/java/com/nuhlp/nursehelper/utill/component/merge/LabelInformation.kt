package com.nuhlp.nursehelper.utill.component.merge

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nuhlp.nursehelper.databinding.ViewInformationLabelBinding

import com.nuhlp.nursehelper.datasource.room.app.Patient
import com.nuhlp.nursehelper.utill.useapp.AppTime
import com.nuhlp.nursehelper.utill.useapp.AppTime.RRN
import com.nuhlp.nursehelper.utill.useapp.AppTime.RRNF
import com.nuhlp.nursehelper.utill.useapp.AppTime.SDF
import com.nuhlp.nursehelper.utill.useapp.AppTime.calculateAge

import java.util.*

class LabelInformation @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    var binding : ViewInformationLabelBinding

    init {
        binding = ViewInformationLabelBinding.inflate(LayoutInflater.from(context),this)
    }

    fun setInfoText(patient:Patient){
        val rrnDate = RRN.parse(patient.rrn)
        val rrnf = RRNF.format(rrnDate)
        binding.apply{
            placeInfoLabelText.text = "알려지지 않음"
            nameInfoLabelText.text = patient.name
            genderInfoLabelText.text = patient.gender
            ageLabelText.text = AppTime.getAge(rrnDate).toString()
            rrnLabelText.text = rrnf
        }
    }
    fun setInfoTextPlace(placeName:String){
        binding.placeInfoLabelText.text = placeName
    }

}