package com.nuhlp.nursehelper.ui.document.report

import android.content.res.Resources
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.domain.DocTemplate

import com.nuhlp.nursehelper.utill.component.merge.LabelInformation

@BindingAdapter("bindViewModel","bindLifecycle")
fun bindInfoLabel(view: LabelInformation, viewModel: ProgressReportViewModel, lifecycleOwner: LifecycleOwner) {

    viewModel.document.asLiveData().observe(lifecycleOwner){
        viewModel.updatePatient(it.patNo)
    }
    viewModel.patient.asLiveData().observe(lifecycleOwner){
        view.setInfoText(it)
    }
}
@BindingAdapter("bindViewModel","bindLifecycle")
fun bindDocLabel(view: TextView, viewModel: ProgressReportViewModel, lifecycleOwner: LifecycleOwner){

}