package com.nuhlp.nursehelper.ui.document

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentDocumentBinding
import com.nuhlp.nursehelper.ui.home.HomeViewModel
import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//todo place 캐쉬 만들기

//todo viewmoel로 데이터 공유하기

class DocumentFragment : BaseDataBindingFragment<FragmentDocumentBinding>() {

    private val _documentViewModel : DocumentViewModel by lazy {
        ViewModelProvider(
            this,
            DocumentViewModel.Factory()
        ).get(DocumentViewModel::class.java)
    }
    private val _homeViewModel :HomeViewModel by lazy {
        ViewModelProvider(
            this
        ).get(HomeViewModel::class.java)
    }

    override val layoutResourceId = R.layout.fragment_document

    override fun onCreateViewAfterBinding() {
        CoroutineScope(Dispatchers.IO).launch {
            val vm = _homeViewModel
            Log.d("DocumentFragment", "${vm.patient.asLiveData().value}")
        }
    }
}