package com.nuhlp.nursehelper.ui.document.report

import android.content.res.Resources
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.domain.DocTemplate

import com.nuhlp.nursehelper.utill.component.merge.LabelInformation
import com.nuhlp.nursehelper.utill.component.merge.ReportContents

@BindingAdapter("bindViewModel","bindLifecycle")
fun bindInfoLabel(view: LabelInformation, viewModel: ProgressReportViewModel, lifecycleOwner: LifecycleOwner) {

    viewModel.document.asLiveData().observe(lifecycleOwner){
        viewModel.updatePatient(it.patNo)
    }
    viewModel.patient.asLiveData().observe(lifecycleOwner){
        view.setInfoText(it)
        viewModel.updatePlace(it.bpNo)
    }
    viewModel.businessPlace.asLiveData().observe(lifecycleOwner){
        view.setInfoTextPlace(it.placeName)
    }
}
@BindingAdapter("bindViewModel","bindLifecycle")
fun bindDocContents(view: ReportContents, viewModel: ProgressReportViewModel, lifecycleOwner: LifecycleOwner) {
    viewModel.document.asLiveData().observe(lifecycleOwner){
        view.binding.reportContents.setText(it.contentsJs)
    }
}