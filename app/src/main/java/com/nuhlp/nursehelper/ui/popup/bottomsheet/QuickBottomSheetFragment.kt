package com.nuhlp.nursehelper.ui.popup.bottomsheet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.databinding.FragmentQuickBottomSheetBinding
import com.nuhlp.nursehelper.utill.base.binding.dialog.BaseBottomDialogDataFragment
import java.util.*

class QuickBottomSheetFragment : BaseBottomDialogDataFragment<FragmentQuickBottomSheetBinding>(),popupUtil {

    override val layoutResourceId = R.layout.fragment_quick_bottom_sheet

        override fun onCreateViewAfterBinding() {
        /*binding.returnBtn.setOnClickListener {
            val random = Random()
            val num = random.nextInt(99)
            sendAndBack(arrayOf("formQuick","arrayOf1","arrayOf2","random : $num"))
        }*/

    }

    override fun sendAndBack(list: Array<String>) {
        findNavController().previousBackStackEntry
            ?.savedStateHandle
            ?.set("quickSentence", list)
        dismiss()
    }

}
