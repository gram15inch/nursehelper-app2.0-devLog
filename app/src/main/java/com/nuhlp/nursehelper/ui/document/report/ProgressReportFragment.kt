package com.nuhlp.nursehelper.ui.document.report

import androidx.lifecycle.ViewModelProvider
import android.util.Log
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.nuhlp.nursehelper.R

import com.nuhlp.nursehelper.databinding.ProgressReportFragmentBinding

import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
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
        _progressReportViewModel.refreshDocument(args.documentNo)
    }

    override fun setOnClickSaveReportButton(v: View?) {
        CoroutineScope(Dispatchers.IO).launch {
            _progressReportViewModel.isChanged.emit(false)
        }
    }
    override fun setOnTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _progressReportViewModel.documentLiveData.value?.let { remote ->
                binding.docReportContents.binding.reportContents.text.toString().let { local ->
                    Log.d("ProgressReportFragment", "remote: ${remote.contentsJs}")
                    Log.d("ProgressReportFragment", "local: ${local}")
                    if (local != "")
                        if (remote.contentsJs != local)
                            CoroutineScope(Dispatchers.IO).launch {
                                _progressReportViewModel.isChanged.emit(true)
                            }
                }
            }
        }
    }

}
