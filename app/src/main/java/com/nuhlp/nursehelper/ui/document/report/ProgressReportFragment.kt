package com.nuhlp.nursehelper.ui.document.report

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.nuhlp.nursehelper.R

import com.nuhlp.nursehelper.databinding.ProgressReportFragmentBinding

import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProgressReportFragment  : BaseDataBindingFragment<ProgressReportFragmentBinding>(),ReportUtil {

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
        binding.reportUtil = this
        _progressReportViewModel.updateDocument(args.documentNo)
    }

    override fun setSaveReportButton(v: View?) {
        binding.docReportContents.binding.sfb.visibility = View.INVISIBLE
    }
    override fun setOnTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(s!=""){
            CoroutineScope(Dispatchers.IO).launch{
                Log.d("ProgressReportFragment","call text1")
                _progressReportViewModel.document.asLiveData().value?.toString().apply {
                    Log.d("ProgressReportFragment","call document: $this")
                    _progressReportViewModel.isChanged.emit(true)
                    // todo 뷰에 상태 플로우 붙이기
                  }
                }

            }
        }

    }
}