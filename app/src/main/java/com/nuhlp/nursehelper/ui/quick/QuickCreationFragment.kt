package com.nuhlp.nursehelper.ui.quick

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentQuickCreationBinding
import com.nuhlp.nursehelper.ui.home.HomeViewModel
import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment

class QuickCreationFragment : BaseDataBindingFragment<FragmentQuickCreationBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_quick_creation

    private val _quickCreationViewModel: QuickCreationViewModel by lazy {
        ViewModelProvider(
            this,
            QuickCreationViewModel.Factory()
        ).get(QuickCreationViewModel::class.java)
    }
    override fun onCreateViewAfterBinding() {
        binding.viewModel =_quickCreationViewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

}