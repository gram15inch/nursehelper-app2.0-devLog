package com.nuhlp.nursehelper.ui.document

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentDocumentBinding
import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment

//todo place 캐쉬 만들기


class DocumentFragment : BaseDataBindingFragment<FragmentDocumentBinding>() {


    val args: DocumentFragmentArgs by navArgs()
    private val _documentViewModel : DocumentViewModel by lazy {
        ViewModelProvider(
            this,
            DocumentViewModel.Factory()
        ).get(DocumentViewModel::class.java)
    }

    override val layoutResourceId = R.layout.fragment_document

    override fun onCreateViewAfterBinding() {
        Log.d("DocumentFragment","onCreate()")
        _documentViewModel.updateDocument(args.documentNo)
    }
}