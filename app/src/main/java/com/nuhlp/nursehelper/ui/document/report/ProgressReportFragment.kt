package com.nuhlp.nursehelper.ui.document.report

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nuhlp.nursehelper.R

import com.nuhlp.nursehelper.databinding.ProgressReportFragmentBinding

import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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


        _progressReportViewModel.let {vm->
            if(args.documentNo!=1)
            vm.refreshDocument(args.documentNo)
            //vm.refreshSentence(args.sentence)
        }
        binding.docReportContents.binding.wfb.setOnClickListener {
            val action = ProgressReportFragmentDirections.actionProgressReportFragmentToQuickCreationFragment()
            this.findNavController().navigate(action)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Array<String>>("quickSentence")?.observe(viewLifecycleOwner){
            _progressReportViewModel.refreshSentence(it)
        }
        Log.d("ProgressReportFragment","onCreate()")

    }

    override fun onStart() {
        super.onStart()
        Log.d("ProgressReportFragment","onStart()")
    }

    override fun onResume() {
        super.onResume()
    Log.d("ProgressReportFragment","onResume()")
    }
    override fun setOnClickSaveReportButton(v: View?) {
        CoroutineScope(Dispatchers.IO).launch {
            _progressReportViewModel.updateDocument(_progressReportViewModel.contentText)
            _progressReportViewModel.isReportChanged.emit(false)
        }
        hideKeyboard()
    }
    override fun setOnTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _progressReportViewModel.contentText = s.toString()
        _progressReportViewModel.contentTextPos = start
        CoroutineScope(Dispatchers.IO).launch {
            _progressReportViewModel.document.value.let { local ->
                if(local.isValid())
                binding.docReportContents.binding.reportContent.text.toString().let { view ->
                    _progressReportViewModel.isReportChanged.emit(local.contentsJs != view)
                }
            }
        }
    }

}
