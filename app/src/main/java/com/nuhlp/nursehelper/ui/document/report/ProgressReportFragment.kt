package com.nuhlp.nursehelper.ui.document.report

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.nuhlp.nursehelper.R

import com.nuhlp.nursehelper.databinding.ProgressReportFragmentBinding

import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment

class ProgressReportFragment  : BaseDataBindingFragment<ProgressReportFragmentBinding>() {

    private val args: ProgressReportFragmentArgs by navArgs()
    private val _progressReportViewModel : ProgressReportViewModel by lazy {
        ViewModelProvider(
            this,
            ProgressReportViewModel.Factory()
        ).get(ProgressReportViewModel::class.java)
    }

    override val layoutResourceId = R.layout.progress_report_fragment

    override fun onCreateViewAfterBinding() {
        binding.viewModel = _progressReportViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        _progressReportViewModel.updateDocument(args.documentNo)
    }
}