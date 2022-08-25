package com.nuhlp.nursehelper.ui.document.report

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData

import com.nuhlp.nursehelper.utill.component.merge.LabelInformation
import com.nuhlp.nursehelper.utill.component.merge.ReportContents

@BindingAdapter("bindViewModel","bindLifecycle")
fun bindInfoLabel(view: LabelInformation, viewModel: ProgressReportViewModel, lifecycleOwner: LifecycleOwner) {

    viewModel.document.asLiveData().observe(lifecycleOwner){
        viewModel.refreshPatient(it.patNo)
    }
    viewModel.patient.asLiveData().observe(lifecycleOwner){
        view.setInfoText(it)
        viewModel.refreshPlace(it.bpNo)
    }
    viewModel.businessPlace.asLiveData().observe(lifecycleOwner){
        view.setInfoTextPlace(it.placeName)
    }
}
@BindingAdapter("bindViewModel","bindLifecycle","bindReportUtil")
fun bindDocContents(view: ReportContents, viewModel: ProgressReportViewModel, lifecycleOwner: LifecycleOwner, reportUtil: ReportUtil) {
    viewModel.document.asLiveData().observe(lifecycleOwner){
        view.binding.reportContents.setText(it.contentsJs)
        view.binding.reportTitle.setText(it.docNo.toString())
    }
    viewModel.isChanged.asLiveData().observe(lifecycleOwner){
        view.binding.sfb.visibility = if(it) View.VISIBLE else View.INVISIBLE
        Log.d("ProgressReportFragment",it.toString())
    }
    view.binding.sfb.setOnClickListener(reportUtil)
    view.binding.reportContents.addTextChangedListener(reportUtil)

}