package com.nuhlp.nursehelper.utill.component.merge

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nuhlp.nursehelper.databinding.ViewInformationLabelBinding
import com.nuhlp.nursehelper.databinding.ViewReportContentsBinding

class ReportContents @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var binding : ViewReportContentsBinding
    init {
        binding = ViewReportContentsBinding.inflate(LayoutInflater.from(context),this)
    }

}