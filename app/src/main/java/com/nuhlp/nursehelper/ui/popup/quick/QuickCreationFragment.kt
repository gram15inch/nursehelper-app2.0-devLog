package com.nuhlp.nursehelper.ui.popup.quick

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import java.util.*

class QuickCreationFragment()  : BaseBottomDialogDataFragment<QuickCreationFragmentBinding>() {

    override val layoutResourceId = R.layout.quick_creation_fragment

    override fun onCreateViewAfterBinding() {
            binding.returnBtn.setOnClickListener {
                /*val action =  QuickCreationFragmentDirections
2                    .actionQuickCreationFragmentToProgressReportFragment(1, arrayOf("line1","line2"))
                this.findNavController().navigate(action)*/
                val random = Random()
                val num = random.nextInt(99)
                val list = arrayOf("formQuick","arrayOf1","arrayOf2","random : $num")
                findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("quickSentence", list)
                dismiss()
            }
    }
    //todo 프래그먼트 -> 다이얼로그 시 퍼즈 왜 안되는지
    //todo 반대시 왜 크리에잇부터 시작 되는지
}
