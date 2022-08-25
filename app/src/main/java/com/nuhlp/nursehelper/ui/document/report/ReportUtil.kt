package com.nuhlp.nursehelper.ui.document.report

import android.text.Editable
import android.text.TextWatcher
import android.view.View

interface ReportUtil: View.OnClickListener, TextWatcher {
    fun setOnClickSaveReportButton(v: View?)
    fun setOnTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)


    override fun onClick(v: View?) { setOnClickSaveReportButton(v) }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        setOnTextChanged(s,start,before,count)
    }
    override fun afterTextChanged(s: Editable?) {}



}
