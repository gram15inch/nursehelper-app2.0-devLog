package com.nuhlp.nursehelper.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.useapp.adapter.PatientsListAdapter

interface HomeUtil {
    fun setPatientRecyclerView(view: RecyclerView)
    fun setDocumentRecyclerView(view: RecyclerView)
}