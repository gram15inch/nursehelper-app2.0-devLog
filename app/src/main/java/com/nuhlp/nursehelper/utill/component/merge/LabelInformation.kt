package com.nuhlp.nursehelper.utill.component.merge

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nuhlp.nursehelper.databinding.ViewInformationLabelBinding

import com.nuhlp.nursehelper.datasource.room.app.Patient
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
        val rrn = patient.rrn
        binding.apply{
            placeInfoLabelText.text = patient.bpNo.toString()
            nameInfoLabelText.text = patient.name
            genderInfoLabelText.text = patient.gender
            ageLabelText.text = "age"
            rrnLabelText.text = patient.rrn
        }
    }
}