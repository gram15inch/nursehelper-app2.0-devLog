package com.nuhlp.nursehelper.ui.popup.quick

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.QuickCreationFragmentBinding
import com.nuhlp.nursehelper.ui.home.HomeFragmentDirections
import com.nuhlp.nursehelper.utill.base.binding.dialog.BaseBottomDialogDataFragment

class QuickCreationFragment()  : BaseBottomDialogDataFragment<QuickCreationFragmentBinding>() {

    override val layoutResourceId = R.layout.quick_creation_fragment

    override fun onCreateViewAfterBinding() {
            binding.returnBtn.setOnClickListener {
                val action =  QuickCreationFragmentDirections
                    .actionQuickCreationFragmentToProgressReportFragment(0, arrayOf("line1","line2"))
                this.findNavController().navigate(action)
            }
    }
    //todo 프래그먼트 -> 다이얼로그 시 퍼즈 왜 안되는지
    //todo 반대시 왜 크리에잇부터 시작 되는지
}
